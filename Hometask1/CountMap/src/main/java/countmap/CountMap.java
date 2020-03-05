package countmap;

import java.util.Map;
import java.util.Set;

public interface CountMap<K> {
    // добавляет элемент в этот контейнер.
    <T extends K> void add(T o);

    //Возвращает количество добавлений данного элемента
    <T extends K> int getCount(T o);

    Set<K> getKeys();

    //Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
    <T extends K> int remove(T o);

    //количество разных элементов
    int size();

    //Добавить все элементы из source в текущий контейнер, при совпадении ключей,     суммироватьзначения
    void addAll(CountMap<? super K> source);

    //Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
    Map<? super K, ? super Integer> toMap();

    //Тот же самый контракт как и toMap(), только всю информацию записать в destination
    void toMap(Map<? extends K, ? extends Integer> destination);
}
