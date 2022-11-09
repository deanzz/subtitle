package fr.noop.subtitle.srt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.noop.subtitle.model.SubtitleCue;
import fr.noop.subtitle.model.SubtitleLine;
import fr.noop.subtitle.model.SubtitleParsingException;
import fr.noop.subtitle.util.SubtitlePlainText;
import fr.noop.subtitle.util.SubtitleTextLine;
import fr.noop.subtitle.util.SubtitleTimeCode;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SrtWriterTest {
    
    private SrtWriter writer = new SrtWriter("utf-8");

    @Test
    public void test() throws IOException, SubtitleParsingException {
        //String json = " [{\"content\":\"一号链接拍拍第二个十九块九的规格更加划算啊\",\"from\":0.0,\"to\":3.995},{\"content\":\"相当于九块九的两份\",\"from\":3.995,\"to\":5.707},{\"content\":\"十九块九\",\"from\":5.707,\"to\":6.468},{\"content\":\"两百四十张\",\"from\":6.468,\"to\":7.419},{\"content\":\"还可以免费一整套工具给大家\",\"from\":7.419,\"to\":9.893},{\"content\":\"对面将买到\",\"from\":9.893,\"to\":10.844},{\"content\":\"就是赚到了软包材质厚硬卡纸\",\"from\":10.844,\"to\":13.317},{\"content\":\"一号链接拍拍\",\"from\":13.317,\"to\":14.459},{\"content\":\"第二个十九块九更划算啊\",\"from\":14.459,\"to\":16.552},{\"content\":\"一百八十克a 四尺寸后裔卡纸\",\"from\":16.552,\"to\":19.025},{\"content\":\"四十张a 四彩纸\",\"from\":19.025,\"to\":20.357},{\"content\":\"一百张\",\"from\":20.357,\"to\":20.927},{\"content\":\"正方材质一百张\",\"from\":20.927,\"to\":22.259},{\"content\":\"一共两百四十张\",\"from\":22.259,\"to\":23.591},{\"content\":\"十九块九\",\"from\":23.591,\"to\":24.352},{\"content\":\"两百四十张\",\"from\":24.352,\"to\":25.303},{\"content\":\"额外包含了安全剪刀固体胶棒双面胶\",\"from\":25.303,\"to\":28.347},{\"content\":\"还有五支勾线笔\",\"from\":28.347,\"to\":29.679},{\"content\":\"一整套都是给大家直接拍啊\",\"from\":29.679,\"to\":31.962},{\"content\":\"软包式材质厚\",\"from\":31.962,\"to\":33.104},{\"content\":\"硬式卡纸材质卡纸组装一号链接拍啊\",\"from\":33.104,\"to\":36.148},{\"content\":\"主播依旧推荐大家拍一号链接\",\"from\":36.148,\"to\":38.621},{\"content\":\"第二个有一整套工具给大家\",\"from\":38.621,\"to\":40.904},{\"content\":\"对你们来讲买到就是赚到了\",\"from\":40.904,\"to\":43.187},{\"content\":\"直接去拍啊\",\"from\":43.187,\"to\":44.138},{\"content\":\"软的薄的是材质\",\"from\":44.138,\"to\":45.47},{\"content\":\"厚的\",\"from\":45.47,\"to\":45.851},{\"content\":\"硬的是卡纸彩纸卡纸组合装\",\"from\":45.851,\"to\":48.134},{\"content\":\"一号链接拍啊\",\"from\":48.134,\"to\":49.275},{\"content\":\"那有些宝贝说主播我家孩子不经常做呀\",\"from\":49.275,\"to\":52.51},{\"content\":\"只是偶尔的时候需要做一次\",\"from\":52.51,\"to\":54.793},{\"content\":\"两次不经常做\",\"from\":54.793,\"to\":55.934},{\"content\":\"但是偶尔还要做一次\",\"from\":55.934,\"to\":57.646},{\"content\":\"两次\",\"from\":57.646,\"to\":58.027},{\"content\":\"看一下缺不缺工具啊\",\"from\":58.027,\"to\":59.739},{\"content\":\"小\",\"from\":59.739,\"to\":59.93}]";
        String json = "[{\"content\":\"价格两百四十张\",\"from\":0.0,\"to\":1.45},{\"content\":\"规格更加划算啊\",\"from\":1.45,\"to\":2.9},{\"content\":\"一百八十克a 四\",\"from\":2.9,\"to\":4.35},{\"content\":\"尺寸\",\"from\":4.35,\"to\":4.764},{\"content\":\"后面卡纸\",\"from\":4.764,\"to\":5.593},{\"content\":\"四十张\",\"from\":5.593,\"to\":6.214},{\"content\":\"a 四彩纸\",\"from\":6.214,\"to\":7.043},{\"content\":\"一百张\",\"from\":7.043,\"to\":7.664},{\"content\":\"正方彩纸一百张\",\"from\":7.664,\"to\":9.114},{\"content\":\"一共两百四十张\",\"from\":9.114,\"to\":10.565},{\"content\":\"十九块九\",\"from\":10.565,\"to\":11.393},{\"content\":\"两百四十张\",\"from\":11.393,\"to\":12.429},{\"content\":\"规格相当于九块九的两份\",\"from\":12.429,\"to\":14.708},{\"content\":\"十九块九\",\"from\":14.708,\"to\":15.536},{\"content\":\"两百四十张啊\",\"from\":15.536,\"to\":16.779},{\"content\":\"额外给你们啊额外包含了安全节到固体胶棒双面胶\",\"from\":16.779,\"to\":21.337},{\"content\":\"还有五支勾线笔\",\"from\":21.337,\"to\":22.787},{\"content\":\"这一整套都是额外给大家的啊\",\"from\":22.787,\"to\":25.48},{\"content\":\"对你们来讲买到就是赚的了\",\"from\":25.48,\"to\":27.966},{\"content\":\"软的薄的是材质\",\"from\":27.966,\"to\":29.416},{\"content\":\"厚的\",\"from\":29.416,\"to\":29.83},{\"content\":\"用的是卡纸彩纸卡纸组合装\",\"from\":29.83,\"to\":32.316},{\"content\":\"一号链接拍拍\",\"from\":32.316,\"to\":33.559},{\"content\":\"第二个十九块九的规格更加划算啊\",\"from\":33.559,\"to\":36.667},{\"content\":\"相当于九块九的两份\",\"from\":36.667,\"to\":38.531},{\"content\":\"十九块九\",\"from\":38.531,\"to\":39.36},{\"content\":\"两百四十张啊\",\"from\":39.36,\"to\":40.603},{\"content\":\"还给你再多一整套工具\",\"from\":40.603,\"to\":42.674},{\"content\":\"对你来讲\",\"from\":42.674,\"to\":43.503},{\"content\":\"买到就是抓得到\",\"from\":43.503,\"to\":44.953},{\"content\":\"直接去拍就行啊\",\"from\":44.953,\"to\":46.403},{\"content\":\"软的薄的是材质\",\"from\":46.403,\"to\":47.853},{\"content\":\"厚的\",\"from\":47.853,\"to\":48.267},{\"content\":\"硬的是卡纸啊\",\"from\":48.267,\"to\":49.51},{\"content\":\"材质卡纸组合装\",\"from\":49.51,\"to\":50.961},{\"content\":\"一号链接直接去拍啊\",\"from\":50.961,\"to\":52.825},{\"content\":\"还想看啥跟主播说\",\"from\":52.825,\"to\":54.482},{\"content\":\"主播再给你们讲啊\",\"from\":54.482,\"to\":56.14},{\"content\":\"软薄式材质后印是卡纸啊\",\"from\":56.37,\"to\":59.017},{\"content\":\"材质卡纸\",\"from\":59.017,\"to\":59.98}]";
        SrtObject subtitle = json2srtObject(json);
    
        if (subtitle != null) {
            writer.write(subtitle, Files.newOutputStream(Paths.get("/Users/deanzhang/work/code/github/subtitle/srt/test1.srt")));
        }
    }
    
    SrtObject json2srtObject(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode nodes = (ArrayNode)mapper.readTree(jsonString);
        if(nodes.isArray()) {
            SrtObject srt = new SrtObject();
            int id = 1;
            for(JsonNode n : nodes) {
                SrtCue cue = new SrtCue();
                cue.setId(String.valueOf(id));
                SubtitleTimeCode startTime = new SubtitleTimeCode((long)(n.get("from").asDouble() * 1000));
                SubtitleTimeCode endTime = new SubtitleTimeCode((long)(n.get("to").asDouble() * 1000));
                cue.setStartTime(startTime);
                cue.setEndTime(endTime);
                List<SubtitleLine> lines = new ArrayList<>();
                SubtitlePlainText text = new SubtitlePlainText(n.get("content").asText());
                SubtitleTextLine line = new SubtitleTextLine();
                line.addText(text);
                lines.add(line);
                cue.setLines(lines);
                srt.addCue(cue);
                id++;
            }
            
            return srt;
        }
        return null;
    }
}
