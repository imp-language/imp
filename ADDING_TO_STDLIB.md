## How to add to the standard library
It is important that before you begin working on an addition to the standard library, you think of a good idea. It is also highly suggested that you write an issue so that the<br>
owners of the repository can hear your idea. If they don't think it's a good idea, or they say that they won't add it, it would probably be a bad idea to write the code.<br>
<br>
If you have run the idea past the owners and they approve the idea, you can begin work on the library. There are several steps to this, and they will be detailed below.
- Step 1) Create the file in the [runtime](https://github.com/imp-language/imp/tree/main/src/main/java/org/imp/runtime) directory, and give it a title that easily explains what the library will do.
- Step 2) Once you have completed the library, create the entry for the library inside of the [glue file](https://github.com/imp-language/imp/blob/main/src/main/java/org/imp/jvm/runtime/Glue.java).
- Step 3) Write an **imp** file that will be tested during the automated test process following the submission of the pull request. Make the file in the [testing directory](https://github.com/imp-language/imp/tree/main/verification).
  You should make sure you write code to test all the features you added in the library.
- Step 4) Write the test for the **imp** file you wrote in step 3. The test must be written in the [CompilerTest file](https://github.com/imp-language/imp/blob/main/src/test/java/org/imp/test/CompilerTest.java). Something important to note, currently, all tests must have a predetermined outcome.
  This means that currently, randomness is not supported in the libraries in the stdlib.
- Step 5) Submit the pull request, and wait. There is a possibility that the test you wrote will fail. If that is the case, you must modify the files in your pull request until the test works.
<br>
It is important to keep in mind that this document does not override the rules stated within the CONTRIBUTING.md file. Make sure you read it through in its entirety before contributing to this project
