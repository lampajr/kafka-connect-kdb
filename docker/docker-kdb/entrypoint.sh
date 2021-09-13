#!/bin/sh
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

# A simple entrypoint script - it:
# 1 - prints a welcome message
# 2 - prints the length of args
# 3 - executes the arguments passed in.
# Naturally it can be customised...to perform checks, start various services, etc.,
# One good use case for the entrypoint would be to pass in a specific script and port to run. in this case a 'master' script like tick.q could start on e.g. port 5000 and a set of worker scripts worker-1.q, worker-2.q etc., could run on 5001, 5002...
echo "Welcome to KDB+ on Docker"
echo $#
#exec "$@"

#exec /q/l32/q -p 5000

# Tickerplant https://code.kx.com/q/wp/rt-tick/
exec /q/l32/q tick.q sym . -p 5010 /tick &
echo "Tickerplant started"

# Real-time kdb subscriber
exec /q/l32/q /tick/r.q :5010 -p 5011 /rdb &

# Start feed handler
exec /q/l32/q /tick/feedhandler.q /feed