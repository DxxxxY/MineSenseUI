package studio.dreamys.icarus.component.sub;

import lombok.SneakyThrows;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import studio.dreamys.icarus.component.Component;
import studio.dreamys.icarus.config.Config;
import studio.dreamys.icarus.event.ComponentEvent;
import studio.dreamys.icarus.util.RenderUtils;
import studio.dreamys.icarus.util.position.Bounds;

import java.awt.Color;
import java.util.regex.Pattern;

public class Field extends Component {
    private boolean focused;

    public Field(String label) {
        super(label, 80, 12);
    }

    @SneakyThrows
    public String getText() {
        return (String) configField.get(null);
    }

    @SneakyThrows
    public void setText(String text) {
        configField.set(null, text);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        //update position
        super.render(mouseX, mouseY);

        //background
        RenderUtils.drawRect(x, y, x + width, y + height, Color.DARK_GRAY.darker().darker());

        //border
        RenderUtils.drawOutline(width, height, x, y, Color.DARK_GRAY);

        //text
        RenderUtils.drawString(focused ? getText() + "_" : getText(), x + 4, y + height / 10, Color.WHITE);

        //label
        RenderUtils.drawString(label, x, y - height / 1.5, Color.WHITE);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        focused = hovered(mouseX, mouseY) && mouseButton == 0;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (focused) {
            if (keyCode == Keyboard.KEY_BACK) {
                if (getText().length() > 0) {
                    //set
                    setText(getText().substring(0, getText().length() - 1));
                }
            } else if (Pattern.compile("[a-zA-Z0-9\\t\\n ./<>?;:\"'`!@#$%^&*()\\[\\]{}_+=~|\\\\-]").matcher(String.valueOf(typedChar)).find()) {
                //set
                setText(getText() + typedChar);
            }

            //save
            Config.save();
            MinecraftForge.EVENT_BUS.post(new ComponentEvent.FieldEvent(this));
        }
    }

    @Override
    public Bounds getBounds() {
        return new Bounds(width, height, 7);
    }
}