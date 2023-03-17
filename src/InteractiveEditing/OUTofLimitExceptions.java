package InteractiveEditing;

/**
 * Класс обработки исключений, которые выводятся при выходе за границу размеров переменных элемента
 */
public class OUTofLimitExceptions extends Exception{
    public OUTofLimitExceptions(String message) {
        super(message);
    }
}
