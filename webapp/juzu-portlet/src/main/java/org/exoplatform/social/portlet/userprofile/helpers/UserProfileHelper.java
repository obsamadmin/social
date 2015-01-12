package org.exoplatform.social.portlet.userprofile.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import juzu.impl.request.Request;
import juzu.request.RenderContext;
import juzu.request.RequestContext;

import org.apache.commons.lang.StringUtils;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.identity.model.Profile;
import org.exoplatform.social.webui.Utils;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.utils.TimeConvertUtils;
import org.json.JSONObject;

public class UserProfileHelper {
  private static final Log LOG = ExoLogger.getLogger(UserProfileHelper.class);
  
  final private static String DATE_FORMAT_MMDDYYYY = "MM/dd/yyyy";
  final private static String DISPLAY_FORMAT_EEDDYYYY = "EE dd, yyyy";
  final private static String KEY = "key";
  final private static String VALUE = "value";
  final private static String URL_KEY = "url";

  /**
   * @param currentProfile
   * @return
   */
  public static Map<String, Object> getDisplayProfileInfo(Profile currentProfile) {
    Map<String, Object> infos = new LinkedHashMap<String, Object>();
    infos.put(Profile.EMAIL, currentProfile.getEmail());
    //
    String jobTitle = currentProfile.getPosition();
    if(!isEmpty(jobTitle)) {
      infos.put(Profile.POSITION, jobTitle);
    }
    String gender = currentProfile.getGender();
    if(!isEmpty(gender)) {
      infos.put(Profile.GENDER, gender);
    }
    //
    putInfoData(currentProfile, infos, Profile.CONTACT_PHONES);
    //
    putInfoData(currentProfile, infos, Profile.CONTACT_IMS);
    //
    putInfoData(currentProfile, infos, Profile.CONTACT_URLS);
    //
    return infos;
  }

  /**
   * @param s
   * @return
   */
  public static boolean isEmpty(String s) {
    return s == null || s.trim().length() == 0;
  }

  /**
   * @param currentProfile
   * @return
   * @throws Exception
   */
  public static List<Map<String, String>> getDisplayExperience(Profile currentProfile) throws Exception {
    List<Map<String, String>> pastExperiences = new ArrayList<Map<String, String>>();
    List<Map<String, String>> lastExperiences = new ArrayList<Map<String, String>>();
    List<Map<String, String>> experiences = getMultiValues(currentProfile, Profile.EXPERIENCES);
    if (experiences != null) {
      for (Map<String, String> srcExperience : experiences) {
        if(isCurrent(srcExperience)) {
          pastExperiences.add(theExperienceData(srcExperience, true));
        } else {
          lastExperiences.add(theExperienceData(srcExperience, false));
        }
      }
      pastExperiences.addAll(lastExperiences);
    }
    return pastExperiences;
  }
  
  public static Profile getCurrentProfile() {
    Identity ownerIdentity = Utils.getOwnerIdentity(true);
    return ownerIdentity.getProfile();
  }
  
  public static Context getContext(ResourceBundle bundle) {
    UserProfileHelper helper = new UserProfileHelper();
    Context ctx = helper.new Context(bundle);
    return ctx;
  }
  
  public static Context getContext(RenderContext renderContext) {
    Locale locale = Locale.ENGLISH;
    RequestContext requestContext = Request.getCurrent().getContext();
    ResourceBundle bundle= requestContext.getApplicationContext().resolveBundle(requestContext.getUserContext().getLocale()) ;

    if (renderContext != null) {
      locale = renderContext.getUserContext().getLocale();
    }
    if (bundle == null) {
      bundle = renderContext.getApplicationContext().resolveBundle(locale);
    }
    
    UserProfileHelper helper = new UserProfileHelper();
    return helper.new Context(bundle);
  }
  
  private static List<Map<String, String>> getMultiValues(Profile currentProfile, String key) {
    return (List<Map<String, String>>) currentProfile.getProperty(key);
  }

  private static void putInfoData(Profile currentProfile, Map<String, Object> infos, String mainKey) {
    List<Map<String, String>> multiValues = getMultiValues(currentProfile, mainKey);
    if (multiValues != null && multiValues.size() > 0) {
      Map<String, String> mainValue = new HashMap<String, String>();
      int counter = 0;
      for (Map<String, String> map : multiValues) {
        if (URL_KEY.equals(map.get(KEY))) {
          ++counter;
          mainValue.put(map.get(KEY) + counter, map.get(VALUE));
        } else {
          mainValue.put(map.get(KEY), map.get(VALUE));
        }
      }
      //
      infos.put(mainKey, mainValue);
    }
  }

  private static Map<String, String> theExperienceData(Map<String, String> srcExperience, boolean isCurrent) {
    Map<String, String> experience = new LinkedHashMap<String, String>();
    putExperienceData(srcExperience, experience, Profile.EXPERIENCES_COMPANY);
    putExperienceData(srcExperience, experience, Profile.EXPERIENCES_POSITION);
    putExperienceData(srcExperience, experience, Profile.EXPERIENCES_DESCRIPTION);
    putExperienceData(srcExperience, experience, Profile.EXPERIENCES_SKILLS);
    //
    putExperienceDate(srcExperience, experience, Profile.EXPERIENCES_START_DATE);
    if(isCurrent) {
      experience.put(Profile.EXPERIENCES_IS_CURRENT, "true");
    } else {
      putExperienceDate(srcExperience, experience, Profile.EXPERIENCES_END_DATE);
    }
    return experience;
  }
  
  private static boolean isCurrent(Map<String, String> srcExperience) {
    return Boolean.valueOf(String.valueOf(srcExperience.get(Profile.EXPERIENCES_IS_CURRENT)));
  }

  private static void putExperienceData(Map<String, String> srcExperience, Map<String, String> destExperience, String key) {
    String value = srcExperience.get(key);
    if (!isEmpty(value)) {
      destExperience.put(key, value);
    }
  }

  private static void putExperienceDate(Map<String, String> srcExperience, Map<String, String> destExperience, String key) {
    String value = srcExperience.get(key);
    if (!isEmpty(value)) {
      try {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FORMAT_MMDDYYYY, Locale.ENGLISH);
        Date date = sd.parse(value.trim());
        destExperience.put(key, TimeConvertUtils.convertXTimeAgo(date, DISPLAY_FORMAT_EEDDYYYY, TimeConvertUtils.MONTH));
      } catch (Exception e) {
        destExperience.put(key, value);
      }
    }
  }

  public static String getLabel(WebuiRequestContext context, String key) {
    ResourceBundle res = context.getApplicationResourceBundle();
    try {
      return res.getString(key);
    } catch (Exception e) {
      return (key.indexOf(".") > 0) ? key.substring(key.lastIndexOf(".") + 1) : key;
    }
  }

  protected static String encodeURI(String input) {
    if (input == null || input.trim().length() == 0) {
      return StringUtils.EMPTY;
    }
    try {
      return URLEncoder.encode(input, "UTF-8")
                      .replaceAll("\\+", "%20")
                      .replaceAll("\\%21", "!")
                      .replaceAll("\\%28", "(")
                      .replaceAll("\\%29", ")")
                      .replaceAll("\\%7E", "~");
    } catch (UnsupportedEncodingException e) {
      return input;
    }
  }
  
  public class Context {
    ResourceBundle rs;

    public Context(ResourceBundle rs) {
      this.rs = rs;
    }

    public String appRes(String key) {
      try {
        return rs.getString(key).replaceAll("'", "&#39;").replaceAll("\"", "&#34;");
      } catch (java.util.MissingResourceException e) {
        LOG.warn("Can't find resource for bundle key " + key);
      } catch (Exception e) {
        LOG.error("Error when get resource bundle key " + key, e);
      }
      return key;
    }
    
    public boolean isString(Object s) {
      return s instanceof String;
    }

    public boolean isURL(String key) {
      if (key == null) return false;
      return key.startsWith(URL_KEY);  
    }
  }
}
