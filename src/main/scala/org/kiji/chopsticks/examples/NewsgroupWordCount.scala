/**
 * (c) Copyright 2013 WibiData, Inc.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kiji.chopsticks.examples

import java.util.NavigableMap

import com.twitter.scalding.Args
import com.twitter.scalding.Job
import com.twitter.scalding.Tsv
import org.apache.avro.util.Utf8

import org.kiji.chopsticks.DSL.KijiInput

/**
 * Counts the words from the newsgroup table.
 *
 * Usage:
 *   chop hdfs <path/to/this/jar> \
 *       org.kiji.chopsticks.examples.NewsgroupWordCount \
 *       --input kiji://.env/default/postings --output ./wordcount.tsv
 */
class NewsgroupWordCount(args: Args) extends Job(args) {
  val inUri: String = args("input")
  val outUri: String = args("output")

  KijiInput(inUri)("info:post" -> 'post)
      // Find all the words in a newsgroup posting's text.
      .flatMap('post -> 'word) { cell: NavigableMap[Long, Utf8] =>
        // Get the posting text.
        val text = cell
            .firstEntry()
            .getValue()
            .toString()

        // Regular expression for matching words. For more information see:
        // http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html.
        val wordRegex = """\b\p{L}+\b""".r

        // Split the text up into words.
        wordRegex
            .findAllIn(text)
            .map { word: String => word.toLowerCase() }
            .toIterable
      }
      // Count the occurrences of each word and store the count in 'count.
      .groupBy('word) { words => words.size('count) }
      // Write the results to a tab-seperated-value file.
      .write(Tsv(outUri))
}

