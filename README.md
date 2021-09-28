## Interview project

I used the clojure cli tools and deps.edn described [here](https://clojure.org/guides/deps_and_cli)
rather than leiningen.

To run the command line app (step1):

`clj -M -m gr-interview.core FILE`

where FILE is  the name of a file to parse/ingest.  More than one file can be specified (seperated by spaces)

sample input files are under the `resources` directory.

To start the web server (step2):

`clj -X gr-interview.core/start`

The server runs on port 4242, and has endpoints:

POST /records - with the body being a string in one of the three formats from step 1.

GET /records/color - returns records sorted by favorite color

GET /records/birthdate - returns records sorted by birthdate

GET /records/name - returns records sorted by last name