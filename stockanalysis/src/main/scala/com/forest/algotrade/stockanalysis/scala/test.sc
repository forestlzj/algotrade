case class pc(cpu:String, mem:Int, hd:Int)
//val a = 1 to 10 foreach(i => List.a(pc("a"+i, i, i )))
val a = 1 to 10
val b = List.tabulate(5)(n => new pc(""+n,n,n))
val c = List.fill(5)(new pc("",1,1))
val vlist = "abv" :: "cd" :: Nil // cons (combile)
val stream = 1 #:: 2 #:: 3 #:: Stream.empty
def fibFrom(a: Int, b: Int): Stream[Int] = a #:: fibFrom(b, a + b)
val h = fibFrom(3,1).take(7)
h.toList
val stack = scala.collection.immutable.Stack.empty
val hasOne = stack.push(1)
hasOne.top
hasOne.pop
val empty = scala.collection.immutable.Queue[Int]()
val has1 = empty.enqueue(1)
val has123 = has1.enqueue(List(2, 3))
val treeset = scala.collection.immutable.TreeSet.empty[Int]
treeset + 1 + 2 + 3 + 3
val bits = scala.collection.immutable.BitSet.empty
val moreBits = bits + 3 + 4 + 4
val map = scala.collection.immutable.ListMap(1->"one", 2->"two")
map(2)







