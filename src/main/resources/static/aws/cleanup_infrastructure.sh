#!/bin/bash

#declaring variables to be used 
clusterName=$AWS_ENV'-cluster'

isReserved(){

	#checking if the resource already exists
	rs=$(aws docdb describe-db-clusters --query 'length(DBClusters[?DBClusterIdentifier==`'$clusterName'`].DBClusterIdentifier)')
	if [ $rs != 0 ]; then
		echo "The resource already exists."
		return 0
	else
		echo "The resource doesnt exist."
		return 1
	fi
}

deleteDocDBCluster(){

	aws docdb delete-db-cluster \
    --db-cluster-identifier $clusterName \
    --skip-final-snapshot --output text
		
}

deleteInstances(){

	aws docdb describe-db-clusters \
	--query 'DBClusters[?DBClusterIdentifier==`'$clusterName'`].DBClusterMembers[*].[DBInstanceIdentifier]' \
	--output text | 
	while read line; 
		do 
		instanceName=$(echo "$line");
		echo "################## Instance name: $instanceName ##################";
		aws docdb delete-db-instance --db-instance-identifier $instanceName --output text;
	done
		
}

main(){

	#checking if the resource already exists
	isReserved
	if [ $? == 0 ]; then
		echo "################## Deleting instances ##################";
		deleteInstances
		if [ $? == 0 ]; then
			echo "################## Instances were deleted successfully ##################";
			echo "################## Deleting cluster ##################";
			deleteDocDBCluster
			if [ $? == 0 ]; then
				echo "Cluster was deleted successfully."
			fi
		fi
	fi
}

#calling main function
main
