package file_transfer;

import java.io.File;

public class Test {

	public static void main(String[] args) {
		String folder = "D:\\Programming\\Eclipse Project\\Computer Network - Sem. 191\\ChatOneToOne\\Server";
		String fileName = "Ryo (supercell) feat. Hatsune Miku - Odds and Ends _ Drum.mp4";
		File file = new File(folder + "\\" + fileName);
		System.out.println(file.exists());
	}

}
