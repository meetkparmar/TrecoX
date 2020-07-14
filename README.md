# TrecoX

## Steps

1. Fork the project.

2. Copy link of your project and clone using this command
    `git clone project-link`

3. Copy project link from be-better-programmer and write this command to connect upstream
     `git remote add upstream project-link`

4. Verify the new upstream repository you've specified for your fork.
    `git remote -v`

5. Open project in android studio

## Note

1. Every time first fetch code from develop branch in develop branch while push the code
    `git fetch upstream https://github.com/Be-Better-Programmer/TrecoX.git`

    If any update on upstream then pull the code
    `git pull upstream develop`

2. Every time create new branch from develop branch (with proper name) then implementing your code
    `git branch` (list of branch)
    `git checkout develop` (jump to develop branch)
    `git checkout -b xyz` (create new branch with name xyz)

3. Every time send Pull Request(PR) from xyz->develop (in GitHub)

4. Every time check your code through ktlint while push the code.