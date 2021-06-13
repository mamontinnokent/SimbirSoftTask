package ru.task;

import ru.task.dao.SiteDAO;
import ru.task.model.Site;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static Site getSiteById(Long id, List<Site> sites) {
        return sites.stream()
                .filter(t -> id == t.getId())
                .toList()
                .get(0);
    }


    public static void main(String[] args) {
        int action;
        List<Site> sites = null;
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Выбери действие:\n " +
                    "1. Список сайтов в базе\n " +
                    "2. Добавьте новый сайт\n " +
                    "3. Удалить всё\n " +
                    "4. Удалить по id\n " +
                    "5. Выход");
            action = in.nextInt();

            switch (action) {
                case 1:
                    sites = SiteDAO.findAll();

                    if (sites.size() == 0) {
                        System.out.println("В базе ещё нет сайтов\n");
                    } else {
                        System.out.println(String.format("%5s%50s", "id", "url"));
                        sites.stream().forEach(site -> System.out.println(String.format("%5d%50s", site.getId(), site.getUrl())));
                        System.out.println("0. Назад\n" + "Либо введите id");
                        Long id = in.nextLong();
                        if (id == 0)
                            break;
                        Site site = getSiteById(id, sites);
                        System.out.println("URL: " + site.getUrl());
                        System.out.println(String.format("%7s%15s", "counter", "word"));
                        site.getWords().stream()
                                .forEach(word -> System.out.println(String.format("%7d%15s", word.getCountOfWord(), word.getWord())));
                    }
                    break;
                case 2:
                    System.out.println("Введите URL ссылку:");
                    String url = in.next();
                    AtomicBoolean flag = new AtomicBoolean(true);
                    sites = SiteDAO.findAll();

                    sites.stream().forEach(site -> {
                        if (site.getUrl().equals(url))
                            flag.set(false);
                    });

                    if (flag.get() == true) {
                        try {
                            new Site(url);
                            System.out.println("Сайт успешно добавлен\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Такой сайт уже есть");
                    }

                    break;
                case 3:
                    SiteDAO.deleteAll();
                    break;
                case 4:
                    sites = SiteDAO.findAll();

                    if (sites.size() == 0) {
                        System.out.println("В базе ещё нет сайтов\n");
                    } else {
                        System.out.println(String.format("%5s%50s", "id", "url"));
                        sites.stream().forEach(site -> System.out.println(String.format("%5d%50s", site.getId(), site.getUrl())));
                        System.out.println("Введите id сайта, который бы Вы хотели удалить:");
                        Long id = in.nextLong();
                        SiteDAO.deleteById(id);
                    }
                    break;
                case 5:
                    return;
            }
        } while (action != -1);
    }
}
