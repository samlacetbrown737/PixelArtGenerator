import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class PixelArtGenerator extends Component {
	public static void main(String[] args) {
		new PixelArtGenerator(args[0], Integer.parseInt(args[1]));
	}

	public PixelArtGenerator(String img, int num) {
		try {
			BufferedImage image = ImageIO.read(this.getClass().getResource(img));
			marchThroughImage(image, num);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	private void marchThroughImage(BufferedImage image, int num) {
		int w = image.getWidth();
		int h = image.getHeight();
		int p = 0;
		int blocks = 0;
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		File f = null;
		for(int i = 0; i < (w-num); i += num) {
			for(int j = 0; j < (h-num); j += num) {
				p = getColor(i, i+num, j, j+num, image);
				blocks++;
				for(int x = i; x < i+num; x++) {
					for (int y = j; y < j+num; y++) {
						img.setRGB(x, y, p);
					}
				}
			}
		}
		try {
			f = new File("/Users/samthedictator/Documents/Code/Current/Output.png");
			ImageIO.write(img, "png", f);
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(w + " x " + h + " image in " + num + " pixel sections, " + blocks + " total sections");
	}

	private int getColor(int startX, int stopX, int startY, int stopY, BufferedImage image) {
		int pixel = 0;
		int alpha = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		int cells = (stopX-startX) * (stopY-startY);
		//System.out.println("Range: " + start + ", " + stop);
		for (int i = startX; i < stopX; i++) {
			for (int j = startY; j < stopY; j++) {
				pixel = image.getRGB(i, j);
				alpha += (pixel >> 24) & 0xff;
				red += (pixel >> 16) & 0xff;
				green += (pixel >> 8) & 0xff;
				blue += (pixel) & 0xff;
			}
		}
		int r = red/cells;
		int g = green/cells;
		int b = blue/cells;
		int a = alpha/cells;
		//System.out.println("rgba: " + r + "," + g + "," + b + "," + a);
		int p = (a<<24) | (r<<16) | (g<<8) | b;
		return p;
	}
}