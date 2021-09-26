## Interview project

I used the clojure cli tools and deps.edn described [here](https://clojure.org/guides/deps_and_cli)
rather than leiningen.

To run the command line app (step1):

`clj -M -m gr-interview.core FILE`

where FILE is  the name of a file to parse/ingest.  More than one file can be specified (seperated by spaces)

sample input files are under the `resources` directory.

To start the web server (step2):
