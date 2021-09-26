#!/bin/bash
#
# Copyright © 2021 Andrea Lamparelli (a.lamparelli95@gmail.com)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


BASE_FOLDER="./docker/"

export BASE_FOLDER=$BASE_FOLDER

if [ -z "$1" ]; then
  echo "Please select which docker-compose command you want to execute"
  echo "For example: 'setup-local.sh up$' or 'setup-local.sh down'"
  exit 1
fi

COMPOSE_FILES=(
  "docker-compose-kdb-local.yml"
  #"docker-compose-kafka-local.yml"
)

echo "Running docker compose script for $1.."

for t in "${COMPOSE_FILES[@]}"; do
  args+=(-f "${BASE_FOLDER}${t}")
done

CMD="docker-compose ${args[@]} ${1} ${2}"

echo "Executing command:"
echo "$CMD"

eval "$CMD"
#docker-compose "${args[@]}" "${1}" "${2}"
#docker-compose "${args[@]}" "$1" "$2"
