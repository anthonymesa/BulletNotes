# BulletNotes

Bullet Notes allows users to save and retrive notes in the command line. A clip is a collection of notes, where all notes must belong to at least one clip (assigning a bullet to multiple clips is not implemented yet). Currently, it only records tasks (bullets) to files (clips), and those same files can read back.

## Execution
Currently I have not been compiling the project into a jar files, I have just been running the class. Using any bash related environment I edited my .bash_aliases in my home directory as such:
```
alias bullet="java -cp (PATH_TO_OUT_FOLDER) Application"
```
Then to update your bash environment run:
```
cd (PATH_TO_USER_HOME_FOLDER)
source .bash_aliases
```
The command syntax is generally then as follows:
```
bullet {COMMAND} [CLIP_NAME] (BULLET)

e.g. bullet -a Test Bring pizza home for dinner
```
In the example above, the clip "Test" is created (assuming it didn't already exist) and the bullet "Bring pizza home for dinner" is written to it (note the lack of quotes around the message).

## Commands
| Command | Description |
| --- | --- |
| -g [CLIP_NAME] | Gets and displays all bullet notes in the desigated clip. | 
| -a [CLIP_NAME] (BULLET) | Adds a bullet note to the designated clip (will create new clip if clip does not already exist). |
| -dc [CLIP_NAME] | Deletes designated clip and all its data. |
| -db [CLIP_NAME] (BULLET_INDEX) | Deletes a Bullet within the specified clip at the provided index (starting at 1) |

The notes that are saved are unencrypted text files, so don't go saving sensitive data with it lol.
