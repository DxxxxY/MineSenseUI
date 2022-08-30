package studio.dreamys.icarus;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import studio.dreamys.icarus.component.Attachment;
import studio.dreamys.icarus.component.Window;
import studio.dreamys.icarus.component.sub.Checkbox;
import studio.dreamys.icarus.component.sub.Keybind;
import studio.dreamys.icarus.config.Config;
import studio.dreamys.icarus.util.RenderUtils;

public class Icarus {
    public static Config config;
    public static Window window;

    public static void init(String modid, Window window) {
        RenderUtils.loadFonts();
        Icarus.window = window; //create window object and store it forever
        config = new Config(modid); //create config which will load settings into window
        MinecraftForge.EVENT_BUS.register(new Icarus());
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e) {
        if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) return;
        if (Keyboard.getEventKeyState()) { //if the key is down
            int keyCode = Keyboard.getEventKey(); //get keycode
            if (keyCode <= 0) return; //ignore invalid keycode
            if (keyCode == window.key) {
                System.out.println("ooooh");
                System.out.println(window.all.size());
                Minecraft.getMinecraft().displayGuiScreen(window);
                return;
            }
            for (Attachment attachment : window.attachments) { //for every attachment
                if (attachment instanceof Keybind) { //if it's a keybind
                    Keybind keybind = (Keybind) attachment; //cast to keybind
                    if (keybind.getKey() == keyCode) { //if the keys match
                        if (keybind.getChild() instanceof Checkbox) { //if the child is a checkbox
                            ((Checkbox) keybind.getChild()).toggle(); //toggle the checkbox
                        }
                    }
                }
            }
        }
    }
}
