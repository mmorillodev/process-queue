package interfaces;
public interface MyComparator<T> {
	default int compare(T str, T str2) {
		return String.valueOf(str).compareTo(String.valueOf(str2));
	}
}