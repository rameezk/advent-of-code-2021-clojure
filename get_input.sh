#!/usr/bin/env bash

echo "Day: "
read -r day

padded_day=$(printf "%02d" "$day")

echo "Downloading puzzle input for day $day"
url="https://adventofcode.com/2021/day/$day/input"
session=$(cat .session)
curl "$url" -H "Cookie: session=$session" --output "./resource/input_day_$padded_day.txt" --silent
