CREATE TABLE matches WITH DESCRIPTION 'A table containing Dota2 matches'
    ROW KEY FORMAT HASH PREFIXED(2)
    WITH LOCALITY GROUP default WITH DESCRIPTION 'main storage' (
      MAXVERSIONS = INFINITY,
      TTL = FOREVER,
      INMEMORY = false,
      COMPRESSED WITH GZIP,
      FAMILY info WITH DESCRIPTION 'basic match information' (
        matchId "long",
        matchSeqId "long",
        startTime "long",
        lobbyType "int"),
      MAP TYPE FAMILY players "long" WITH DESCRIPTION 'information for each player in the match');
