# Shaun - Project Portfolio Page

## Overview


### Summary of Contributions
[RepoSense Link](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/#/widget/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&chartGroupIndex=14&chartIndex=3)

#### Tag Management
I contributed to the implementation of tag management functionality in the Zettel note-taking system, enabling both 
global tag management and per-note tagging. Functionalities include:

1. Global Tag Initialisation and Synchronisation
- Implemented logic to validate and maintain the global `tags.txt` file on system startup.
- Checks tags from all existing notes across repositories and updates tags list to ensure consistency between each 
repository's `index.txt` and `tags.txt`.

2. Global Tag Creation (`NewTagCommand`)
- Adds a new tag to the global list of tags
- Validates tags from user inputs
- Checks for duplicates
- Updates storage of tags in `tags.txt`

