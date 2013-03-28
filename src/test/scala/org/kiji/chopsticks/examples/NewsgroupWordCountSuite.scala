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

import scala.collection.mutable.Buffer

import com.twitter.scalding.JobTest
import com.twitter.scalding.Tsv
import org.apache.avro.util.Utf8

import org.kiji.chopsticks.DSL._
import org.kiji.chopsticks.KijiSuite
import org.kiji.chopsticks.Resources.doAndRelease
import org.kiji.schema.EntityId
import org.kiji.schema.KijiTable
import org.kiji.schema.layout.KijiTableLayout
import org.kiji.schema.layout.KijiTableLayouts

class NewsgroupWordCountSuite extends KijiSuite {
  // Set up Kiji table for input.
  val layout: KijiTableLayout = {
    KijiTableLayouts.getTableLayout("org/kiji/chopsticks/examples/layout/postings.json")
  }
  val testInput: List[(EntityId, NavigableMap[Long, Utf8])] = List(
      ( id("row01"), singleton(new Utf8("hello hello hello     hello")) ),
      ( id("row02"), singleton(new Utf8("hello    \nworld")) ),
      ( id("row03"), singleton(new Utf8("world")) ),
      ( id("row04"), singleton(new Utf8("hello")) ))
  val uri: String = doAndRelease(makeTestKijiTable(layout)) { table: KijiTable =>
    table.getURI().toString()
  }

  // A function to validate the test output.
  def validateTest(outputBuffer: Buffer[(String, Int)]) {
    val outMap = outputBuffer.toMap

    // Validate that the output is as expected.
    assert(6 === outMap("hello"))
    assert(2 === outMap("world"))
  }

  test("NewsgroupWordCount counts words using scalding's local mode.") {
    JobTest(new NewsgroupWordCount(_))
        .arg("input", uri)
        .arg("output", "outputFile")
        .source(KijiInput(uri)("info:post" -> 'post), testInput)
        .sink(Tsv("outputFile"))(validateTest)
        .run
        .finish
  }

  test("NewsgroupWordCount counts words using hadoop.") {
    JobTest(new NewsgroupWordCount(_))
        .arg("input", uri)
        .arg("output", "outputFile")
        .source(KijiInput(uri)("info:post" -> 'post), testInput)
        .sink(Tsv("outputFile"))(validateTest)
        .runHadoop
        .finish
  }
}
