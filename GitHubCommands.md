# How to download and edit pieces of a GitHub repo without cloning the entire repo

## 1. `Enabling sparse-checkout`

These commands are used to enable sparse-checkout, which allows users to pull, edit, and push folder(s) from a GitHub repo without having to clone the entire repo. Enabling sparse-checkout only needs to be done once. To enable sparse-checkout, use the following commands: 

```git
git clone --no-checkout <repo_url>
```
This command is used to clone into the repo you will be working on without downloading the entire repo as soon as you clone into it. The url for this repo(RoutineHelperApp) is https://github.com/tdavis24/RoutineHelperApp.git

```git
cd <repo_name>
```
This command is used to change the directory to the cloned repo folder. It should typically be "cd RoutineHelperApp".

```git
git sparse-checkout init --cone
```
This command is used to enable sparse-checkout on the repo previously cloned. This command only needs to be done once.

```git
git sparse-checkout set <folder/path>
```
This command is used to set what folders you would like to actually clone(download to your local folder). The folder path should be RoutineHelperApp/src/...

```git
git checkout <branch_name>
```
This is used to actually checkout the folder(s) you will be editing. If we are using branches, make sure you use the branch dedicated to yourself when checking out the folder(s). If we are not using branches, the branch name will always be main.

## 2. `Pulling folder(s) after enabling sparse-checkout`

These commands are used to pull the folder(s) from the GitHub repo that has been enabled for sparse-checkout. To pull the folder(s), use the following commands:

```git
git sparse-checkout reapply
```
This command is used to reapply the sparse-checkout rules for the repo. Always run this command before trying to pull any new changes from the github repo if sparse-checkout is enabled. This command can also be used to troubleshoot any missing folders in your local repo.

```git
git pull origin <branch>
```
This command is used to actually pull any changes to the repo into your local folder. If there are changes to the repo that you are not seeing locally, try running the sparse-checkout reapply and this command. The branch name will typically be main to pull changes from the main branch into your own branch.

## 3. `Committing folder(s) to GitHub repo`

These commands are used to commit any edited folder(s) to the GitHub repo. To commit the folder(s), use the following commands:

```git
git add <path/to/modified/folder>
```
This command is used to tell GitHub where the edited folders are stored locally. An easy way to get the path to the modified folder(s) is to right-click on the highest(closest to src folder) edited folder and select "Copy Path".

```git
git commit -m "Enter your commit comments here"
```
This command is used to commit any changes to the repo. When making commits, you are required to have a comment explaining any changes made to the folders and if there is anything that still needs to be done to the codeabse that was changed, i.e. testing, debugging, finishing program logic, etc. If any commits are made that do not have a comment, they will be rolled back until a comment has been added.

```git
git push origin <branch_name>
```
This command is used to actually upload the changes made to the repo. The branch name should match the branch you used when checking out the folders, which could be main or a branch dedicated to you alone, depending on if we are using branches or not.

## 4. `Creating a merge request ro merge your branch into the main branch`

In order to create a merge request to merge any changes made to the repository on your branch, you will need to go to the GitHub repository on the web and follow the instructions on the website.