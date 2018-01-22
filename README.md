# darts

A Clojure library designed to ... well, that part is up to you.

## Usage

FIXME

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.



https://wlc.fn.sportradar.com/pdcsrcardiff/en/Europe:Berlin/gismo/event_fullfeed/-18/24
https://wlc.fn.sportradar.com/pdcsrcardiff/en/Europe:Berlin/gismo/event_fullfeed/0/24

".../0/24" Where "0" is the current day.
[-365 - 0] range would provide the last year worth of games


https://wlc.fn.sportradar.com/pdcsrcardiff/en/Europe:Berlin/gismo/match_timeline/13451765

Where the ID for the match is located in the previous "event_fullfeed" JSON data:

"doc" -> "data" -> [ "realcategories" -> [ "tournaments" -> [ "matches" -> [ "_id" ]]]]
