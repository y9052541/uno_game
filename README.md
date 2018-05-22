# uno_game

改动：
  1. 在unoframe中增加了四个JLabel，分别是turn[0..3]，用来标识现在是谁的round
  2. 将unoframe中 nowwho JLabel 的位置做了修改，放到了自己的牌的下方，箭头用来指示方向
  3. 更改了各个文件中的 sendnow 和 rcvnow 函数，多传递了一个 direc 方向信息

注：
  其他文件只改动了sendnow 和 rcvnow 函数，unoframe中的改动用 "//---------------- changed" 注释标出
