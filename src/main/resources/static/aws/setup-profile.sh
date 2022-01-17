#!/bin/bash

echo Setting aws profile
aws configure import --csv file://$(cred.secureFilePath)  
aws configure list-profiles