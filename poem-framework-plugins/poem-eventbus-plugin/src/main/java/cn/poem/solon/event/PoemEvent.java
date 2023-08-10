package cn.poem.solon.event;

/**
 * 事件抽象
 * @author hans
 */
public abstract class PoemEvent {
    protected final Object data;

    public Object getData() {
        return data;
    }
    public PoemEvent(Object data){
        this.data = data;
    }
}
