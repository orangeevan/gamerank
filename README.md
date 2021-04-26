# gamerank
主要用于游戏排行榜，异步定时排序，。使用泛型支持各种自定义排行信息
排行类型分RankType 和 RankSubType，
  RankType：主要是不同类型（不同功能）的排行榜。
   RankType下还有RankSubType（子类型），是RankType的一种细化。不如战力排行榜中分（世界排行，法师排行，战士排行）
 WaveType 是排行排行数据是往上升还是网下降。
 主要排序逻辑：
  RankManager.commit(RankKey key, IRankingObject data)。提交排行数据，这个只是提交变化的数据，还没涉及到改变排行榜。
  RankManager.flushRanks(RankKey key, boolean forceRank)。这个才是真正的进行排行逻辑。
