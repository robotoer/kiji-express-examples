import com.twitter.scalding._

import org.kiji.express._
import org.kiji.express.DSL._

class Dota2Importer(args: Args) extends Job(args) {
  MultipleTextLineFiles(inFiles)
      .map { JSON.parseJSON(
}

import net.minidev.json.JSONValue
import net.minidev.json.JSONArray
import net.minidev.json.JSONObject

import scala.collection.JavaConversions._

object JSON {
  def parseJSON(s: String) = new ScalaJSON(JSONValue.parse(s))
  def makeJSON(a: Any): String = a match {
    case m: Map[String, Any] => m.map {
      case (name, content) => "\"" + name + "\":" + makeJSON(content)
    }.mkString("{", ",", "}")
    case l: List[Any] => l.map(makeJSON).mkString("[", ",", "]")
    case l: java.util.List[Any] => l.map(makeJSON).mkString("[", ",", "]")
    case s: String => "\"" + s + "\""
    case i: Int => i.toString
  }

  implicit def ScalaJSONToString(s: ScalaJSON) = s.toString
  implicit def ScalaJSONToInt(s: ScalaJSON) = s.toInt
  implicit def ScalaJSONToDouble(s: ScalaJSON) = s.toDouble
}

case class JSONException extends Exception

class ScalaJSONIterator(i: java.util.Iterator[java.lang.Object]) extends Iterator[ScalaJSON] {
  def hasNext = i.hasNext()
  def next() = new ScalaJSON(i.next())
}

class ScalaJSON(o: java.lang.Object) extends Seq[ScalaJSON] with Dynamic {
  override def toString: String = o.toString
  def toInt: Int = o match {
    case i: Integer => i
    case _ => throw new JSONException
  }
  def toDouble: Double = o match {
    case d: java.lang.Double => d
    case f: java.lang.Float => f.toDouble
    case _ => throw new JSONException
  }
  def apply(key: String): ScalaJSON = o match {
    case m: JSONObject => new ScalaJSON(m.get(key))
    case _ => throw new JSONException
  }

  def apply(idx: Int): ScalaJSON = o match {
    case a: JSONArray => new ScalaJSON(a.get(idx))
    case _ => throw new JSONException
  }
  def length: Int = o match {
    case a: JSONArray => a.size()
    case m: JSONObject => m.size()
    case _ => throw new JSONException
  }
  def iterator: Iterator[ScalaJSON] = o match {
    case a: JSONArray => new ScalaJSONIterator(a.iterator())
    case _ => throw new JSONException
  }

  def selectDynamic(name: String): ScalaJSON = apply(name)
  def applyDynamic(name: String)(arg: Any) = {
    arg match {
      case s: String => apply(name)(s)
      case n: Int => apply(name)(n)
      case u: Unit => apply(name)
    }
  }
}




















































val inFiles: Array[String] = Array(
  "/shared/dota-matches/matches_0-1000000",
  "/shared/dota-matches/matches_1000000-2000000.gz",
  "/shared/dota-matches/matches_10000000-11000000",
  "/shared/dota-matches/matches_100000000-101000000.gz",
  "/shared/dota-matches/matches_101000000-102000000.gz",
  "/shared/dota-matches/matches_102000000-103000000.gz",
  "/shared/dota-matches/matches_103000000-104000000.gz",
  "/shared/dota-matches/matches_104000000-105000000.gz",
  "/shared/dota-matches/matches_105000000-106000000.gz",
  "/shared/dota-matches/matches_106000000-107000000.gz",
  "/shared/dota-matches/matches_107000000-108000000.gz",
  "/shared/dota-matches/matches_108000000-109000000.gz",
  "/shared/dota-matches/matches_109000000-110000000.gz",
  "/shared/dota-matches/matches_11000000-12000000.gz",
  "/shared/dota-matches/matches_110000000-111000000.gz",
  "/shared/dota-matches/matches_111000000-112000000.gz",
  "/shared/dota-matches/matches_112000000-113000000.gz",
  "/shared/dota-matches/matches_113000000-114000000.gz",
  "/shared/dota-matches/matches_114000000-115000000.gz",
  "/shared/dota-matches/matches_115000000-116000000.gz",
  "/shared/dota-matches/matches_116000000-117000000.gz",
  "/shared/dota-matches/matches_117000000-118000000.gz",
  "/shared/dota-matches/matches_118000000-119000000.gz",
  "/shared/dota-matches/matches_119000000-120000000.gz",
  "/shared/dota-matches/matches_12000000-13000000",
  "/shared/dota-matches/matches_120000000-121000000.gz",
  "/shared/dota-matches/matches_121000000-122000000.gz",
  "/shared/dota-matches/matches_122000000-123000000.gz",
  "/shared/dota-matches/matches_123000000-124000000.gz",
  "/shared/dota-matches/matches_124000000-125000000.gz",
  "/shared/dota-matches/matches_125000000-126000000.gz",
  "/shared/dota-matches/matches_126000000-127000000.gz",
  "/shared/dota-matches/matches_127000000-128000000.gz",
  "/shared/dota-matches/matches_128000000-129000000.gz",
  "/shared/dota-matches/matches_129000000-130000000.gz",
  "/shared/dota-matches/matches_13000000-14000000.gz",
  "/shared/dota-matches/matches_130000000-131000000.gz",
  "/shared/dota-matches/matches_131000000-132000000.gz",
  "/shared/dota-matches/matches_132000000-133000000.gz",
  "/shared/dota-matches/matches_133000000-134000000.gz",
  "/shared/dota-matches/matches_134000000-135000000.gz",
  "/shared/dota-matches/matches_135000000-136000000.gz",
  "/shared/dota-matches/matches_136000000-137000000.gz",
  "/shared/dota-matches/matches_137000000-138000000.gz",
  "/shared/dota-matches/matches_138000000-139000000.gz",
  "/shared/dota-matches/matches_14000000-15000000",
  "/shared/dota-matches/matches_140000000-141000000.gz",
  "/shared/dota-matches/matches_141000000-142000000.gz",
  "/shared/dota-matches/matches_142000000-143000000.gz",
  "/shared/dota-matches/matches_143000000-144000000.gz",
  "/shared/dota-matches/matches_144000000-145000000.gz",
  "/shared/dota-matches/matches_145000000-146000000.gz",
  "/shared/dota-matches/matches_146000000-147000000.gz",
  "/shared/dota-matches/matches_147000000-148000000.gz",
  "/shared/dota-matches/matches_148000000-149000000.gz",
  "/shared/dota-matches/matches_149000000-150000000.gz",
  "/shared/dota-matches/matches_15000000-16000000.gz",
  "/shared/dota-matches/matches_16000000-17000000.gz",
  "/shared/dota-matches/matches_17000000-18000000.gz",
  "/shared/dota-matches/matches_18000000-19000000.gz",
  "/shared/dota-matches/matches_19000000-20000000.gz",
  "/shared/dota-matches/matches_2000000-3000000",
  "/shared/dota-matches/matches_20000000-21000000.gz",
  "/shared/dota-matches/matches_21000000-22000000.gz",
  "/shared/dota-matches/matches_22000000-23000000.gz",
  "/shared/dota-matches/matches_23000000-24000000.gz",
  "/shared/dota-matches/matches_24000000-25000000.gz",
  "/shared/dota-matches/matches_25000000-26000000.gz",
  "/shared/dota-matches/matches_26000000-27000000.gz",
  "/shared/dota-matches/matches_27000000-28000000.gz",
  "/shared/dota-matches/matches_28000000-29000000.gz",
  "/shared/dota-matches/matches_29000000-30000000.gz",
  "/shared/dota-matches/matches_3000000-4000000.gz",
  "/shared/dota-matches/matches_30000000-31000000.gz",
  "/shared/dota-matches/matches_31000000-32000000.gz",
  "/shared/dota-matches/matches_32000000-33000000.gz",
  "/shared/dota-matches/matches_33000000-34000000.gz",
  "/shared/dota-matches/matches_34000000-35000000.gz",
  "/shared/dota-matches/matches_35000000-36000000.gz",
  "/shared/dota-matches/matches_36000000-37000000.gz",
  "/shared/dota-matches/matches_37000000-38000000.gz",
  "/shared/dota-matches/matches_38000000-39000000.gz",
  "/shared/dota-matches/matches_39000000-40000000.gz",
  "/shared/dota-matches/matches_4000000-5000000",
  "/shared/dota-matches/matches_40000000-41000000.gz",
  "/shared/dota-matches/matches_41000000-42000000.gz",
  "/shared/dota-matches/matches_42000000-43000000.gz",
  "/shared/dota-matches/matches_43000000-44000000.gz",
  "/shared/dota-matches/matches_44000000-45000000.gz",
  "/shared/dota-matches/matches_45000000-46000000.gz",
  "/shared/dota-matches/matches_46000000-47000000.gz",
  "/shared/dota-matches/matches_47000000-48000000.gz",
  "/shared/dota-matches/matches_48000000-49000000.gz",
  "/shared/dota-matches/matches_49000000-50000000.gz",
  "/shared/dota-matches/matches_5000000-6000000.gz",
  "/shared/dota-matches/matches_50000000-51000000.gz",
  "/shared/dota-matches/matches_51000000-52000000.gz",
  "/shared/dota-matches/matches_52000000-53000000.gz",
  "/shared/dota-matches/matches_53000000-54000000.gz",
  "/shared/dota-matches/matches_54000000-55000000.gz",
  "/shared/dota-matches/matches_55000000-56000000.gz",
  "/shared/dota-matches/matches_56000000-57000000.gz",
  "/shared/dota-matches/matches_57000000-58000000.gz",
  "/shared/dota-matches/matches_58000000-59000000.gz",
  "/shared/dota-matches/matches_59000000-60000000.gz",
  "/shared/dota-matches/matches_6000000-7000000",
  "/shared/dota-matches/matches_60000000-61000000.gz",
  "/shared/dota-matches/matches_61000000-62000000.gz",
  "/shared/dota-matches/matches_62000000-63000000.gz",
  "/shared/dota-matches/matches_63000000-64000000.gz",
  "/shared/dota-matches/matches_64000000-65000000.gz",
  "/shared/dota-matches/matches_65000000-66000000.gz",
  "/shared/dota-matches/matches_66000000-67000000.gz",
  "/shared/dota-matches/matches_67000000-68000000.gz",
  "/shared/dota-matches/matches_68000000-69000000.gz",
  "/shared/dota-matches/matches_69000000-70000000.gz",
  "/shared/dota-matches/matches_7000000-8000000.gz",
  "/shared/dota-matches/matches_70000000-71000000.gz",
  "/shared/dota-matches/matches_71000000-72000000.gz",
  "/shared/dota-matches/matches_72000000-73000000.gz",
  "/shared/dota-matches/matches_73000000-74000000.gz",
  "/shared/dota-matches/matches_74000000-75000000.gz",
  "/shared/dota-matches/matches_75000000-76000000.gz",
  "/shared/dota-matches/matches_76000000-77000000.gz",
  "/shared/dota-matches/matches_77000000-78000000.gz",
  "/shared/dota-matches/matches_78000000-79000000.gz",
  "/shared/dota-matches/matches_79000000-80000000.gz",
  "/shared/dota-matches/matches_8000000-9000000",
  "/shared/dota-matches/matches_80000000-81000000.gz",
  "/shared/dota-matches/matches_81000000-82000000.gz",
  "/shared/dota-matches/matches_82000000-83000000.gz",
  "/shared/dota-matches/matches_83000000-84000000.gz",
  "/shared/dota-matches/matches_84000000-85000000.gz",
  "/shared/dota-matches/matches_85000000-86000000.gz",
  "/shared/dota-matches/matches_86000000-87000000.gz",
  "/shared/dota-matches/matches_87000000-88000000.gz",
  "/shared/dota-matches/matches_88000000-89000000.gz",
  "/shared/dota-matches/matches_89000000-90000000.gz",
  "/shared/dota-matches/matches_9000000-10000000.gz",
  "/shared/dota-matches/matches_90000000-91000000.gz",
  "/shared/dota-matches/matches_91000000-92000000.gz",
  "/shared/dota-matches/matches_92000000-93000000.gz",
  "/shared/dota-matches/matches_93000000-94000000.gz",
  "/shared/dota-matches/matches_94000000-95000000.gz",
  "/shared/dota-matches/matches_95000000-96000000.gz",
  "/shared/dota-matches/matches_96000000-97000000.gz",
  "/shared/dota-matches/matches_97000000-98000000.gz",
  "/shared/dota-matches/matches_98000000-99000000.gz",
  "/shared/dota-matches/matches_99000000-100000000.gz"
)
