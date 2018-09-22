package frame.view;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.openhft.hashing.LongHashFunction;

public class Scan_Object_For_duplication {

	public ConcurrentMap<Path, Long> BuildMyMapInformation(String dir) throws IOException {
		// TODO Auto-generated method stub
		return Files.walk(Paths.get(dir)).parallel().filter(f -> f.toFile().isFile())
				.filter(f -> f.toString().endsWith("jpg") || f.toString().endsWith("jpeg")
						|| f.toString().endsWith("gif") || f.toString().endsWith("png") || f.toString().endsWith("JPG"))
				.collect(Collectors.toConcurrentMap(Path::toAbsolutePath, p -> GetHashImage_Sample(p)));
	}

	public List<Long> Get_All_keys_That_Have_more_than_one(ConcurrentMap<Path, Long> name_key) {
		// TODO Auto-generated method stub
		return name_key.values().parallelStream().filter(v -> v != 0)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().parallelStream()
				.filter(v -> v.getValue() > 1).map(Map.Entry::getKey).collect(Collectors.toList());
	}

	public void Get_All_Path_need_to_deleted(List<Long> list_of_all_keys_more_than_one,
			ConcurrentMap<Path, Long> name_key, ConcurrentMap<Long, Set<Path>> key_List_Path) {
		// TODO Auto-generated method stub

		list_of_all_keys_more_than_one.parallelStream().forEach(key -> {

			Set<Path> temp = new TreeSet<Path>();
			temp = name_key.entrySet().parallelStream().filter(e -> e.getValue().equals(key)).map(Entry::getKey)
					.collect(Collectors.toSet()); // get path

			key_List_Path.put(key, temp);

		});

	}

	private long GetHashImage_Sample(Path f) {
		try {
			File file = f.toFile();
			RandomAccessFile random = new RandomAccessFile(file, "r");
			byte[] b = new byte[1024];
			byte[] b2 = new byte[1024];

			random.readFully(b);
			random.seek(file.length() / 2);
			random.readFully(b2);
			random.close();

			byte[] combined = new byte[b.length + b2.length];

			System.arraycopy(b, 0, combined, 0, b.length);

			System.arraycopy(b2, 0, combined, b.length, b2.length);

			return LongHashFunction.xx().hashBytes(combined);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	public static void MakeDirKey_And_CopyTo(Entry<Long, Set<Path>> entry,String dir) throws IOException {
		if (entry.getValue().size() != 0) {
			Path dest = Paths.get(dir + "/duplicated" + "/" + String.valueOf(entry.getKey()));
			if (!Files.exists(dest))
				Files.createDirectories(dest);

			entry.getValue().parallelStream().forEach(e -> {
				try {
					Files.copy(e, Paths.get(dest.toString() + File.separator + e.getFileName()), REPLACE_EXISTING);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			});
		}

	}

	public void Copy_all_Files_to_duplicated(String dir , ConcurrentMap<Long, Set<Path>> key_List_Path) throws IOException {
		// TODO Auto-generated method stub

		Path dest = Paths.get(dir + "/duplicated");
		if (!Files.exists(dest))
			Files.createDirectories(dest);

		key_List_Path.entrySet().parallelStream().forEach(p -> {

			try {
				MakeDirKey_And_CopyTo(p,dir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

}
