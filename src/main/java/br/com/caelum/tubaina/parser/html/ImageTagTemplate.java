package br.com.caelum.tubaina.parser.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

public class ImageTagTemplate {

	// TODO: make it work more gracefully... i.e., eliminate this workaround
	private static final String RELATIVEPATH = "$$RELATIVE$$/";

	public String parse(final String path, final String options, boolean shouldUseHTMLWidth) {
		String imgsrc = FilenameUtils.getName(path);
		String output = "<img src=\"" + RELATIVEPATH + imgsrc + "\" ";
		String width = "";

		Pattern label = Pattern.compile("(?s)(?i)label=(\\w+)%?");
		Matcher labelMatcher = label.matcher(options);
		
		if (labelMatcher.find()) {
			output = output + "id=\"" + labelMatcher.group(1) + "\" ";
		}
		
		Pattern description = Pattern.compile("(?s)(?i)\"(.+?)\"");
		Matcher descriptionMatcher = description.matcher(options);
		
		if (shouldUseHTMLWidth && getScale(options) != null) {
		    width = "width='" +getScale(options) + "%' "; 
		}
		
		// The image is resized when copied
		if (descriptionMatcher.find()) {
			output = output + width + "alt=\"" + descriptionMatcher.group(1) + "\" />";
		} else {
			output = output + width + "alt=\"" + imgsrc + "\" />";
		}
		
		return output;
	}

	public Integer getScale(final String string) {
		if (string == null) {
			return null;
		}
		Pattern horizontalScale = Pattern.compile("(?s)(?i)w=(\\d+)%?");
		Matcher sMatcher = horizontalScale.matcher(string);

		if (sMatcher.find()) {
			return Integer.parseInt(sMatcher.group(1));
		}
		return null;
	}
}