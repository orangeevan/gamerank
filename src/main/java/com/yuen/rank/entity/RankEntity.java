package com.yuen.rank.entity;

/**
 * 排行榜子类型
 * @author: yuanchengyan
 * @description:
 * @since 10:23 2021/4/7
 */
public class RankEntity implements IEntity<RankKey> {

    private RankKey key;

    private String jsonData;

    @Override
    public RankKey getId() {
        return key;
    }

    @Override
    public boolean serialize() {
        return true;
    }

    @Override
    public boolean deserialize() {
        return false;
    }

    public RankKey getKey() {
        return key;
    }

    public void setKey(RankKey key) {
        this.key = key;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public int hashCode() {
        return this.getKey().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RankEntity)) {
            return false;
        }
        RankEntity entity = (RankEntity) obj;
        return entity.getKey().equals(this.key);
    }
}
