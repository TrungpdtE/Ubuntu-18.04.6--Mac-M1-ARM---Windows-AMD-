import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ASEANCaseCount {

    //Danh sách các nước ASEAN cần lọc
    private static final Set<String> ASEAN_COUNTRIES=new HashSet<>(Arrays.asList(
        "Brunei Darussalam", "Cambodia", "Indonesia", "Lao People's Democratic Republic",
        "Malaysia", "Myanmar", "Philippines", "Singapore", "Thailand", "Viet Nam"
    ));

    //======= Mapper Class =======
    public static class CaseMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line=value.toString().trim();

            //Bỏ qua dòng tiêu đề (header)
            if (key.get() == 0 && line.contains("WHO Region")){
                return;
            }

            //Chia dữ liệu theo dấu tab ('\t')
            String[] fields=line.split("\t");

            //Kiểm tra số lượng cột để tránh lỗi
            if (fields.length>= 13){
                String country=fields[0].trim(); //Lấy tên quốc gia
                
                //Kiểm tra nếu quốc gia thuộc ASEAN
                if (ASEAN_COUNTRIES.contains(country)){
                    try {
                        //Chuyển đổi số ca nhiễm thành số nguyên (bỏ phần thập phân)
                        int cases=(int) Double.parseDouble(fields[2].replace(",", "").trim());
                        
                        //Xuất dữ liệu (key: tên quốc gia, value: tổng số ca nhiễm)
                        context.write(new Text(country), new IntWritable(cases));
                    } catch (NumberFormatException e){
                        System.err.println("⚠️ Lỗi chuyển đổi số ca nhiễm: " + fields[2]);
                    }
                }
            }
        }
    }

    //======= Reducer Class =======
    public static class CaseReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int totalCases=0;

            //Cộng dồn số ca nhiễm từ Mapper
            for (IntWritable val : values){
                totalCases += val.get();
            }

            //Xuất kết quả (quốc gia, tổng số ca nhiễm)
            context.write(key, new IntWritable(totalCases));
        }
    }

    //======= Main Method (Driver) =======
    public static void main(String[] args) throws Exception {
        Configuration conf=new Configuration();
        Job job=Job.getInstance(conf, "ASEAN COVID-19 Case Count");

        job.setJarByClass(ASEANCaseCount.class);
        job.setMapperClass(CaseMapper.class);
        job.setReducerClass(CaseReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));  //Đường dẫn file input
        FileOutputFormat.setOutputPath(job, new Path(args[1])); //Đường dẫn file output

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

