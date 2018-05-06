# DesignReport

## a. What will a jumper do if the location in front of it is empty, but the location two cells in front contains a flower or a rock?
>* 如果jumper前面第一个格子是空的，但是第二个格子是花的情况下，jumper可以继续前进，覆盖并移除花朵。
>* 如果jumper前面第一个格子是空的，但是第二个格子是岩石的情况下，jumper不可以继续前进，必须顺时针旋转45度再次判断能否继续前进。

## b. What will a jumper do if the location two cells in front of the jumper is out of the grid?
如果jumper前面两个格子超出了网格范围，那么jumper不会向前跳，而是从顺时针方向选择另外一个方向来移动。

## c. What will a jumper do if it is facing an edge of the grid?
同理，当jumper面对着边缘时，jumper不会前进，而是顺时针方向开始寻找另外一个可以移动的方向。

## d. What will a jumper do if another actor (not a flower or a rock) is in the cell that is two cells in front of the jumper?
如果jumper前面两个格子遇到其他actor，类如Bug，那么jumper也不会直接前进，而是从顺时针每次旋转45度选择另外一个方向移动。

## e. What will a jumper do if it encounters another jumper in its path?
如果jumper在跳跃路上遇到其他jumper，那么它也不会直接前进，而是从顺时针每次旋转45度选择另外一个方向移动。

## f. Are there any other tests the jumper needs to make?
还要测试jumper移动后，是否原地会留下花朵；jumper能否正确转向。

