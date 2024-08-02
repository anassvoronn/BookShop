package org.nastya.generator;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.nastya.dao.AuthorDao;
import org.nastya.dao.AuthorToBookDao;
import org.nastya.dao.BookDao;
import org.nastya.dao.database.AuthorDatabaseDao;
import org.nastya.dao.database.AuthorToBookDatabaseDao;
import org.nastya.dao.database.BookDatabaseDao;
import org.nastya.entity.Country;
import org.nastya.entity.Gender;
import org.nastya.entity.Genre;
import org.nastya.utils.DataSourceFactory;
import org.nastya.utils.ObjectCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuthorToBookGeneratorTest {
    private static AuthorDao authorDao;
    private static BookDao bookDao;
    private static AuthorToBookDao authorToBookDao;
    private static HikariDataSource dataSource;

    private static final String[] NAMES = {
            "Алексей Смирнов", "Мария Иванова", "Сергей Петров", "Анна Кузнецова", "Дмитрий Сидоров",
            "Екатерина Федорова", "Андрей Михайлов", "Ольга Васильева", "Николай Попов",
            "Татьяна Николаева", "Игорь Захаров", "Светлана Григорьева", "Владимир Лебедев",
            "Наталья Морозова", "Артем Ковалев", "Юлия Соколова", "Денис Тихонов", "Виктория Павлова",
            "Роман Костин", "Дарья Фролова", "Павел Яковлев", "Анастасия Орлова", "Константин Белов",
            "Елена Чернова", "Михаил Буров", "Ирина Селезнева", "Владислав Куликов", "Алёна Романова",
            "Степан Алексеев", "Маргарита Сафонова", "Василий Громов", "Полина Шевченко",
            "Александр Кузьмин", "Валентина Зайцева", "Сергей Соловьев", "Надежда Крылова", "Тимур Гусев",
            "Виктория Мельникова", "Ярослав Синицын", "Ксения Лазарева", "Арсений Федотов",
            "Вероника Тихонова", "Владислав Сучков", "Людмила Григорьева", "Станислав Сорокин",
            "Нина Костенко", "Всеволод Широков", "Инна Костина", "Анатолий Рябов", "Оксана Сидорова",
            "Тимофей Ермаков", "Светлана Дьякова", "Артемий Лебедев", "Алина Семенова", "Денис Громов",
            "Юлия Власова", "Илья Романов", "Елизавета Назарова", "Кирилл Мартынов", "Алла Шаповалова",
            "Николай Крылов", "Марина Бондарева", "Дмитрий Тарасов", "Анна Сергеева", "Борис Фомичев",
            "Татьяна Петрова", "Вячеслав Степанов", "Лариса Беляева", "Олег Кузнецов", "Валерия Костюкова",
            "Всеволод Долгов", "Надежда Соловьева", "Максим Коваленко", "Инна Лебедева", "Алексей Филиппов",
            "Елена Тихонова", "Константин Гончаров", "Ольга Воробьева", "Роман Сидорович", "Дарья Григорьева",
            "Артемий Титов", "Виктория Станиславская ", "Игорь Прохоров ", "Анастасия Сидоренко ",
            "Николай Ларин ", "Полина Бурмистрова ", "Ярослав Хмельницкий ", "Елизавета Беспалова ",
            "Валентин Петрович ", "Лилия Чистякова ", "Евгений Сергеев ", "Анжела Королева ",
            "Анатолий Баранов ", "Вера Кузнецова ", "Степан Широков ", "Оксана Гречишникова ",
            "Всеволод Шевцов ", "Татьяна Зайцева ", "Артем Петровский ", "Нина Смирнова"
    };

    private static final String[] TITLES = {
            "Слёзы на увядшем цветке", "Проекция", "Комплекс друга детства", "Все злодеи глупцы",
            "Фальстарт", "Обойдёмся без рукопожатий", "Искажённая душа", "Сойти с дороги",
            "Ежедневная подработка", "Зона любовных органичений", "Скрытое в тени", "Декабрь",
            "Секрет в моей голове", "Дом пять", "Фейковый союз", "Дух спасения", "Вот это удача",
            "Серьёзная шутка", "Член гильдии по соседству", "Исправить", "Страсть", "Подсветить",
            "Звёздная орбита", "Проект БОТАНИК", "Прибой", "Дорогой плюшевый мишка", "19 дней однажды",
            "Заклятый друг", "Личная жизнь моего соседа", "Непреднамеренная история любви", "Шах и мат",
            "Шампанское и розы", "Между случайностью и неизбежностью", "Доставщик пиццы", "Партнёр на пол ставки",
            "Идеальный приятель", "Остерегайся полнолуния", "Возмездие", "Никакой морали", "Меч и цветок",
            "Органиченный тираж", "Страсное влечение", "Белая кровь", "Мне в тягость твоя игра",
            "Теория эгоистичной любви", "Ночь у берега", "Город кислоты", "Удачный кредит", "Охота",
            "Золотая клетка", "Игрушка", "Сезон дождей", "Мир фигур", "Чёрный орёл сахары",
            "Шахматная доска цвета аквамарин", "Пять привил: легенда о сыновьей почтительности"
    };

    private static final Country[] COUNTRIES = {
            Country.UKRAINE, Country.JAPAN, Country.USA, Country.GEORGIA, Country.ENGLAND,
            Country.SPAIN, Country.RUSSIA, Country.LITHUANIA, Country.CANADA, Country.CHINA
    };

    private static final String[] GENRES = {
            Genre.ADVENTURE.name(), Genre.COMEDY.name(), Genre.NOVEL.name(), Genre.DYSTOPIA.name(), Genre.HORROR.name(),
            Genre.DOCUMENTARY.name(), Genre.FANTASY.name(), Genre.CLASSIC.name(), Genre.PSYCHOLOGY.name(),
            Genre.SCIENCE_FICTION.name(), Genre.DETECTIVE.name()
    };


    @BeforeAll
    static void beforeAll() {
        DataSourceFactory factory = new DataSourceFactory();
        factory.readingFromFile();
        dataSource = factory.getDataSource();
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        authorDao = new AuthorDatabaseDao(jdbcTemplate);
        bookDao = new BookDatabaseDao(jdbcTemplate);
        authorToBookDao = new AuthorToBookDatabaseDao(jdbcTemplate);
    }

    @AfterAll
    static void afterAll() {
        dataSource.close();
    }

    @Test
    void fillTheDatabase() {
        Random random = new Random();
        List<Integer> authorIds = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        int authorId;
        int bookId;
        for (int i = 0; i < 40; i++) {
            String authorName = NAMES[random.nextInt(NAMES.length)];
            String dateOfBirth = "196" + random.nextInt(8) + "-01-01";
            String dateOfDeath = random.nextBoolean() ?
                    (random.nextInt(currentYear - 2000 + 1) + 2000) + "-01-01" : null;
            Gender gender = random.nextBoolean() ? Gender.MALE : Gender.FEMALE;
            Country country = COUNTRIES[random.nextInt(COUNTRIES.length)];

            authorId = authorDao.insert(ObjectCreator.createAuthor(authorName, dateOfBirth, dateOfDeath, gender, country));
            authorIds.add(authorId);
        }

        for (int i = 0; i < 70; i++) {
            String bookTitle = TITLES[random.nextInt(TITLES.length)];
            bookId = bookDao.insert(
                    ObjectCreator.createBook(
                            bookTitle,
                            String.valueOf(1980 + random.nextInt(20)),
                            Genre.valueOf(GENRES[random.nextInt(GENRES.length)])
                    )
            );

            int randomAuthorId = authorIds.get(random.nextInt(authorIds.size()));
            authorToBookDao.insert(ObjectCreator.createAuthorToBook(randomAuthorId, bookId));
        }
    }

    @Test
    void cleanTheDatabase() {
        authorDao.deleteAll();
        bookDao.deleteAll();
        authorToBookDao.deleteAll();
    }
}
