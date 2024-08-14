package com.github.jcactusdev.web;

import com.github.jcactusdev.Config;
import com.github.jcactusdev.model.*;
import com.github.jcactusdev.storage.Storage;
import com.github.jcactusdev.util.DateUtil;
import com.github.jcactusdev.util.HtmlUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            doGetActionDefault(request, response);
        } else {
            switch (action) {
                case "view":
                    doGetActionView(request, response);
                    break;
                case "add":
                    doGetActionAdd(request, response);
                    break;
                case "edit":
                    doGetActionEdit(request, response);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown action: " + action);
            }
        }
    }

    private void doGetActionDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("resumes", storage.getAllSorted());
        request.getRequestDispatcher("/WEB-INF/jsp/resumelist.jsp").forward(request, response);
    }

    private void doGetActionView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        Resume resume = storage.get(uuid);
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("/WEB-INF/jsp/resumeview.jsp").forward(request, response);
    }

    private void doGetActionAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("resume", Resume.EMPTY);
        request.setAttribute("action", "add");
        request.getRequestDispatcher("/WEB-INF/jsp/resumeedit.jsp").forward(request, response);
    }

    private void doGetActionEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        Resume resume = storage.get(uuid);
        for (SectionType type : SectionType.values()) {
            Section section = resume.getSection(type);
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    if (section == null) {
                        section = TextSection.EMPTY;
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    if (section == null) {
                        section = ListSection.EMPTY;
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    OrganizationSection orgSection = (OrganizationSection) section;
                    List<Organization> emptyFirstOrganizations = new ArrayList<>();
                    emptyFirstOrganizations.add(Organization.EMPTY);
                    if (orgSection != null) {
                        for (Organization org : orgSection.getOrganizations()) {
                            List<Organization.Position> emptyFirstPositions = new ArrayList<>();
                            emptyFirstPositions.add(Organization.Position.EMPTY);
                            emptyFirstPositions.addAll(org.getPositions());
                            emptyFirstOrganizations.add(new Organization(org.getHomePage(), emptyFirstPositions));
                        }
                    }
                    section = new OrganizationSection(emptyFirstOrganizations);
                    break;
            }
            resume.setSection(type, section);
        }
        request.setAttribute("resume", resume);
        request.setAttribute("action", "edit");
        request.getRequestDispatcher("/WEB-INF/jsp/resumeedit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            throw new NullPointerException("action");
        } else {
            switch (action) {
                case "add":
                    doPostAdd(request, response);
                    break;
                case "edit":
                    doPostEdit(request, response);
                    break;
                case "delete":
                    doPostDelete(request, response);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown action: " + action);
            }
        }
    }

    private void doPostAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Resume resume = new Resume();
        fillResume(resume, request, response);
        storage.save(resume);
        response.sendRedirect("resume?uuid=" + resume.getUuid() + "&action=view");
    }

    private void doPostEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        Resume resume = storage.get(uuid);
        if (resume == null) {
            throw new IllegalArgumentException("resume not found");
        }
        fillResume(resume, request, response);
        storage.update(resume);
        response.sendRedirect("resume?uuid=" + uuid + "&action=view");
    }

    private void fillResume(Resume resume, HttpServletRequest request, HttpServletResponse response) {
        resume.setFullName(request.getParameter("fullName"));

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.getContacts().remove(type);
            } else {
                resume.setContact(type, value);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE, PERSONAL:
                        resume.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT, QUALIFICATIONS:
                        resume.setSection(type, new ListSection(value.split("\\n")));
                        break;
                    case EDUCATION, EXPERIENCE:
                        List<Organization> orgs = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Organization.Position> positions = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(new Organization.Position(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                orgs.add(new Organization(new Link(name, urls[i]), positions));
                            }
                        }
                        resume.setSection(type, new OrganizationSection(orgs));
                        break;
                }
            }
        }
    }

    private void doPostDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        storage.delete(uuid);
        response.sendRedirect("resume");
    }

}
