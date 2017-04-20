package controller.commands.helper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by vlad on 19.04.17.
 */
public interface ParamExtractor {
    <T extends Enum<T>> T getEnumParamOrNull(HttpServletRequest request, String paramName, Class<T> enumType);

    Date getDateParamOrNull(HttpServletRequest request, String paramName);

    Integer getIntParamOrNull(HttpServletRequest request, String paramName);

    String getStringParamOrNull(HttpServletRequest request, String paramName);

    /*throws ControlException if params would not be found*/

    Date getDateParam(HttpServletRequest request, String paramName);

    Integer getIntParam(HttpServletRequest request, String paramName);

    String getStringParam(HttpServletRequest request, String paramName);

}
