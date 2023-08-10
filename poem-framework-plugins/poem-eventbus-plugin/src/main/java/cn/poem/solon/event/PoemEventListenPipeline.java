package cn.poem.solon.event;

import org.noear.solon.core.event.EventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 事件监听管道
 *
 * @author noear
 * @since 2.4
 */
public class PoemEventListenPipeline<PoemEvent> implements PoemEventListener<PoemEvent> {
    private List<EH> list = new ArrayList<>();

    /**
     * 添加监听
     */
    public void add(PoemEventListener<PoemEvent> listener) {
        add(0, listener);
    }

    /**
     * 添加监听（带顺序位）
     */
    public void add(int index, PoemEventListener<PoemEvent> listener) {
        list.add(new EH(index, listener));
        list.sort(Comparator.comparing(EH::getIndex));
    }

    /**
     * 移除监听
     */
    public void remove(EventListener<PoemEvent> listener) {
        for (int i = 0; i < list.size(); i++) {
            if (listener.equals(list.get(i).listener)) {
                list.remove(i);
                i--;
            }
        }
    }

    @Override
    public void onEvent(PoemEvent event) throws Throwable {
        for (EH h : list) {
            h.listener.onEvent(event);
        }
    }

    static class EH<PoemEvent> {
        int index;
        PoemEventListener<PoemEvent> listener;

        EH(int index, PoemEventListener<PoemEvent> listener) {
            this.index = index;
            this.listener = listener;
        }

        public int getIndex() {
            return index;
        }
    }
}
