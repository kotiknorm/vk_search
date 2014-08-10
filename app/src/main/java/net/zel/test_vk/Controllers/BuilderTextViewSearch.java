package net.zel.test_vk.Controllers;

import android.widget.TextView;

import com.vk.sdk.api.VKApiConst;

import net.zel.test_vk.Models.BuilderQuery;
import net.zel.test_vk.Utils.Constants;

/**
 * Created by Алексей on 10.08.2014.
 */
public class BuilderTextViewSearch {

    private final BuilderQuery bd;

    private final TextView textParam;

    public BuilderTextViewSearch(BuilderQuery _builderQuery, TextView _textView){

        bd = _builderQuery;
        textParam = _textView;
    }

    public void updateTextView(){
        String line = "";
        String i = bd.param.get(VKApiConst.SEX);
        if (i.equals(Constants.ANY_SEX))
            line = "Любой, ";
        if (i.equals(Constants.MALE))
            line = "Женский, ";
        if (i.equals(Constants.FAMALE))
            line = "Мужской, ";
        line = line + "от " + bd.param.get(VKApiConst.AGE_FROM) + " до " + bd.param.get(VKApiConst.AGE_TO);
        textParam.setText(line);
    }
}
