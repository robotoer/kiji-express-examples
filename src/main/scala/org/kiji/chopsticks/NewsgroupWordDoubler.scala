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

import com.twitter.scalding._
import org.apache.avro.util.Utf8

import org.kiji.chopsticks.DSL._
import org.kiji.schema.EntityId

/**
 * This is a trivial example that demonstrates writing to a Kiji table.
 *
 * Writes a repeated version of each word in the info:word column to the info:doubleword column.
 * For example, if a row contains "hello" in the info:word column, this Doubler writes "hellohello"
 * to the info:doubleword column in that row.
 *
 * Usage:
 *   chop jar org.kiji.chopsticks.examples.NewsgroupWordDoubler \
 *   --input kiji://.env/default/words --output kiji://.env/default/words
 */
class NewsgroupWordDoubler(args: Args) extends Job(args) {
  val tableUri: String = args("input")
  val resultUri: String = args("output")

  KijiInput(tableUri)("info:word" -> 'word)
      .map(('entityId, 'word) -> 'doubleword) { tuple: (EntityId, NavigableMap[Long, Utf8]) =>
        val (_, words) = tuple
        val word = getMostRecent(words).toString()
        "%s%s".format(word, word)
      }
      .write(KijiOutput(resultUri)('doubleword -> "info:doubleword"))
}