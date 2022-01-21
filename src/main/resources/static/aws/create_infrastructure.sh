#!/bin/bash

#declaring variables to be used
clusterName=$AWS_ENV'-cluster'

isReserved(){

	#checking if the resource already exists
	rs=$(aws docdb describe-db-clusters --query 'length(DBClusters[?DBClusterIdentifier==`'$clusterName'`].DBClusterIdentifier)')
	if [ $rs != 0 ]; then
		echo "######################### The resource already exists. #########################"
		return 0
	else
		echo "######################### The resource doesnt exist. #########################"
		return 1
	fi
}

createDocDBCluster(){

	aws docdb create-db-cluster \
		--db-cluster-identifier $clusterName \
	    --engine docdb \
	    --master-username $AWS_DOCDB_USER \
	    --master-user-password $AWS_DOCDB_PASS \
	    --port 27017 \
		--vpc-security-group-ids sg-0dff1c0c116919b3f \
		--tags Key=environment,Value=$AWS_ENV \
		--preferred-maintenance-window Sun:20:30-Sun:21:00 \
		--no-deletion-protection \
		--db-cluster-parameter-group-name dev-param-grp\
		--source-region $AWS_REGION \
		--output text \
		--profile $AWS_PROFILE
		
}

attachInstances(){

	rs='None'
	echo "######################### Creating instance. #########################"	
	while [ $rs != 'available' ]
	do
	   echo "######################### Cluster status. $rs #########################"
	   sleep 10
	   rs=$(aws docdb describe-db-clusters --query 'DBClusters[?DBClusterIdentifier==`'$clusterName'`].Status | [0]' --output text)
	done
	aws docdb create-db-instance \
    --db-cluster-identifier $clusterName \
    --db-instance-class db.t4g.medium \
    --db-instance-identifier $clusterName'-instance' \
    --tags Key=environment,Value=$AWS_ENV \
    --output text \
    --engine docdb
}

main(){

	#checking if the resource already exists
	isReserved
	if [ $? == 1 ]; then
		echo "######################### Creting documentdb cluster #########################"
		createDocDBCluster
		if [ $? == 0 ]; then
			echo "######################### Documentdb cluster created successfully. #########################"
			attachInstances
			if [ $? == 0 ]; then
			echo "######################### Instance attached properly to the cluster. #########################"
		fi
		fi
		
	fi
}

#calling main function
main
