package com.gti.redirects.Util;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Map;

/**
 * Created by xach on 5/2/17.
 */
public class ViewUtil {
    public static String render(Map<String, Object> model, String templatePath) {
        return handlebarEngine().render(new ModelAndView(model, templatePath));
    }

    private static HandlebarsTemplateEngine handlebarEngine() {
        HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();
        return templateEngine;
    }
}
