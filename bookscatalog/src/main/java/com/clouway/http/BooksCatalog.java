package com.clouway.http;

import com.clouway.core.*;
import freemarker.template.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class BooksCatalog extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BooksRepository booksRepository=DependencyManager.getDependency(BooksRepositoryFactory.class).getRepository();
        PageBean<Book> pageBean = DependencyManager.getDependency(PageBean.class);

        String pagePointer = req.getParameter("pagePointer");
        List<Book> books= booksRepository.get();

        pageBean.setList(books);

        if (pagePointer==null){
            printPage(resp.getWriter(),pageBean.firstPage(),pageBean.getCurrentPageNumber());
        }
        if ("lastPage".equals(pagePointer)){
            printPage(resp.getWriter(),pageBean.lastPage(),pageBean.getCurrentPageNumber());
        }
        if ("firstPage".equals(pagePointer)){
            printPage(resp.getWriter(),pageBean.firstPage(),pageBean.getCurrentPageNumber());
        }
        if ("nextPage".equals(pagePointer)){
            printPage(resp.getWriter(),pageBean.next(),pageBean.getCurrentPageNumber());
        }
        if ("previousPage".equals(pagePointer)){
            printPage(resp.getWriter(),pageBean.previous(),pageBean.getCurrentPageNumber());
        }
    }

    private void printPage(PrintWriter printWriter,List<Book> books, int currentPageNumber){
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(BooksCatalog.class, "");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("title", "Books catalog");
        input.put("books", books);
        input.put("currentPageNumber", currentPageNumber);

        Template template=null;
        try {
            template = cfg.getTemplate("booksCatalog.ftl");
            template.process(input, printWriter);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}