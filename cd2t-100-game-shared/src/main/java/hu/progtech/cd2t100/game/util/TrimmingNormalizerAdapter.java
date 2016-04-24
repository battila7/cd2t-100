package hu.progtech.cd2t100.game.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang3.StringUtils;

public class TrimmingNormalizerAdapter extends XmlAdapter<String, String> {
  @Override
  public String marshal(String text) {
  	return text;
  }

  @Override
  public String unmarshal(String s) throws Exception {
    return StringUtils.normalizeSpace(s);
  }
}
