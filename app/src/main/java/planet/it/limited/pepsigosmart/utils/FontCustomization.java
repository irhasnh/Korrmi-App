package planet.it.limited.pepsigosmart.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Tarikul on 4/12/2018.
 */

public class FontCustomization {
    public Typeface LucidaCalligraphy;
    public Typeface TimesNewRoman;
  //  public Typeface headLandOne;
    Context context;

    public FontCustomization(Context mContext){
        this.context = mContext;
        this.LucidaCalligraphy = Typeface.createFromAsset(context.getAssets(),"lucida_font.ttf") ;
        this.TimesNewRoman = Typeface.createFromAsset(context.getAssets(),"blacknit.ttf") ;
        //this.headLandOne = Typeface.createFromAsset(context.getAssets(),"headland_one.ttf") ;

    }
    public Typeface getLucidaCalligraphy(){
        return LucidaCalligraphy;
    }
    public Typeface getTimesNewRoman(){
        return TimesNewRoman;
    }
}
