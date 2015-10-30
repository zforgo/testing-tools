package hu.zforgo.common.util;

@FunctionalInterface
public interface DefaultServiceCreator<T> {

	T create();
}
