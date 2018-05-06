## 1.第一个程序利用自己实现的链表来建立表格，参照已有BoundedGrid代码，重写其中的函数，将利用数组的原代码更改为使用链表。

Q:Why is this a more time-efficient implementation than BoundedGrid?

A: 因为数组实现的方法中各个函数，例如getOccupiedLocatons()，get(),put(),remove()等函数是利用两个for循环，时间复杂度为col*row。而在链表中的不需要搜索全图，仅需要通过链头的数组找到相对应的元素，时间复杂度为col*n，其中n为稀疏图的被占据的位置个数。

## 2.第二个程序是利用java中map来建立表格，利用Map<Location, E>存储被占据的元素。

Q:Which methods of UnboundedGrid could be used without change?

A:getOccupiedLocation(),get(),put(),remove()这四个函数不需要改变就可以直接使用

Q:Fill in the following chart to compare the expected Big-Oh efficiencies for each implementation of the SparseBoundedGrid.
Let r = number of rows, c = number of columns, and n = number of occupied locations

A:

| Methods | SparseGridNode version  |  LinkedList<OccupantInCol> version  | HashMap version | TreeMap version |
| --------   | -----:  | -----:  | -----:  | -----:  |
| getNeighbors     | O(c)  | O(c)  | O(1)  | O(log n)  |
| getEmptyAdjacentLocations    | O(c)  | O(c)  | O(1)   | O(log n)  |
| getOccupiedAdjacentLocations   |  O(c)  | O(c)  | O(1)  | O(log n) |
| getOccupiedLocations     | O(r+n)  | O(r+n)  | O(n)  | O(n)  |
| get        | O(c)  | O(c)  | O(1)  | O(log n)  |
| put        | O(c)  | O(c)  | O(1)  | O(log n)  |
| remove    | O(c)  | O(c)  | O(1)  | O(log n)  |

## 3.第三个程序是利用可变数组实现无边界网格，当需要更大的网格时，网格是行列会自动扩增两倍。

Q:What is the Big-Oh efficiency of the get method? What is the efficiency of the put method when the row and column index values are within the current array bounds? What is the efficiency when the array needs to be resized?

A:get()的O复杂度为O(1);而put不需要扩增时的O复杂度也是O(1),当需要扩增时，由于复制的开销，所以复杂度变为O(old_col*old_row)。