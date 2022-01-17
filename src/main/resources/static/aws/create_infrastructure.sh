#!/bin/bash

echo "Listing s3 buckets."
aws s3 ls --profile $AWS_PROFILE