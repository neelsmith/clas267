# Generating graphs and reports


## Data sets

- Synchronisms go in `time-datasets`
- Catalogs of events got in `eventcatalogs`


## Scripts

The repository includes scala scripts you can use in an sbt console.  From a terminal in this directory:



1. Start up sbt: `sbt`
2. Set the scala version to 2.12.1: `++ 2.12.1`
3. Open a console: `console`
4. Load the "unify" script:  `:load unify.sc`


Scripts you can run allow you to:

- graph the entire network of events
- graph the web of events for an individual project
- highlight the shortest path from a given event to the epoch of Nabonassar in Ptolemy's *Canon of Kings*
