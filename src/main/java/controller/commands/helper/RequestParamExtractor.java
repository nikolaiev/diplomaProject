package controller.commands.helper;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Created by vlad on 19.04.17.
 */
public class RequestParamExtractor implements ParamExtractor{

    private final static String LOG_MESSAGE_PARSING_ERROR_INTEGER_PARAMETER_FORMAT
            = "Can't parse Integer parameter";

    private final static String LOG_MESSAGE_PARSING_ERROR_DATE_PARAMETR_FORMAT
            = "Can't parse Date parameter";

    private final static String LOG_MESSAGE_PARSING_ERROR_ENUM_PARAMETR_FORMAT
            = "Can't parse Enum parameter";

    private final static String LOG_MESSAGE_PARSING_ERROR_NO_VALUE_FOUND
            = "Param value is null";

    public RequestParamExtractor(){}

    @Override
    public <T extends Enum<T>> T getEnumParamOrNull(HttpServletRequest request, String paramName, Class<T> enumType) {
        try {
            return Optional.ofNullable(request.getParameter(paramName))
                    .filter(e->!"".equals(e))
                    .map(e -> Enum.valueOf(enumType, e)).orElse(null);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(LOG_MESSAGE_PARSING_ERROR_ENUM_PARAMETR_FORMAT);
        }
    }

    @Override
    public Date getDateParamOrNull(HttpServletRequest request, String paramName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return Optional.ofNullable(request.getParameter(paramName))
                .filter(e->!"".equals(e))
                .map(source -> {
                    try {
                        return format.parse(source);
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(LOG_MESSAGE_PARSING_ERROR_DATE_PARAMETR_FORMAT);
                    }
                })
                .orElse(null);

    }

    @Override
    public Integer getIntParamOrNull(HttpServletRequest request, String paramName) {
        try {
            return Optional.ofNullable(request.getParameter(paramName))
                    .filter(e->!"".equals(e))
                    .map(Integer::parseInt)
                    .orElse(null);

        }catch (NumberFormatException e){
            throw new IllegalArgumentException(LOG_MESSAGE_PARSING_ERROR_INTEGER_PARAMETER_FORMAT);
        }
    }

    @Override
    public String getStringParamOrNull(HttpServletRequest request, String paramName) {
            return Optional.ofNullable(request.getParameter(paramName))
                    .filter(e->!"".equals(e))
                    .orElse(null);
    }

    @Override
    public Date getDateParam(HttpServletRequest request, String paramName) {
        return Optional.ofNullable(getDateParamOrNull(request,paramName)).orElseThrow(()->
            new IllegalArgumentException(LOG_MESSAGE_PARSING_ERROR_NO_VALUE_FOUND)
        );
    }


    @Override
    public Integer getIntParam(HttpServletRequest request, String paramName) {
        return Optional.ofNullable(getIntParamOrNull(request,paramName)).orElseThrow(()->
            new IllegalArgumentException(LOG_MESSAGE_PARSING_ERROR_NO_VALUE_FOUND )

        );
    }

    @Override
    public String getStringParam(HttpServletRequest request, String paramName) {
        return Optional.ofNullable(getStringParamOrNull(request,paramName)).orElseThrow(()->
            new IllegalArgumentException(LOG_MESSAGE_PARSING_ERROR_NO_VALUE_FOUND )
        );
    }
}
