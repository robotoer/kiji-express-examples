CREATE TABLE postings WITH DESCRIPTION 'Newsgroup posts in the 20Newsgroups dataset.'
ROW KEY FORMAT HASH PREFIXED(2)
WITH LOCALITY GROUP default WITH DESCRIPTION 'Main storage.' (
  MAXVERSIONS = 1,
  TTL = FOREVER,
  COMPRESSED WITH GZIP,
  FAMILY info WITH DESCRIPTION 'Basic information' (
    post "string" WITH DESCRIPTION 'The text of the post.',
    group "string" WITH DESCRIPTION 'The newsgroup that this posting belongs to.',
    postLength "int" WITH DESCRIPTION 'The number of words in this posting.'
  )
);
