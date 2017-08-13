#include <iostream>
#include <cstring>
#include <cstdint>
using namespace std;

class Tiny_http_accept_part {

    private:
        int client, cgi, numchars;
        char buffer[1024], method[255], url[255], path[512];
        char *query_string = NULL;
        //struct stat st;
    public:
        Tiny_http_accept_part(int);

        /*
        int test() {
            return cgi;
        }
        */

        void accept_request(void *arg) {
            numchars = get_line(client, buffer, sizeof(buffer));
            int i = 0, j = 0;
            while(buffer[i] != ' ' && i < sizeof(buffer) - 1) {
                method[i] = buffer[i];  // read the request
                i++;
            }
            method[i] = '\0'; // add escape character to the end, make it a cstring
            j = i;  // j start from the first white space

            if(strcmp(method, "GET") && strcmp(method, "POST")) {
                //unimplemented(client);
                return;
            }

            if(strcmp(method, "POST") == 0)
                cgi = 1;  //不懂
            
            i = 0;
            while(buffer[j] == ' ' && j < numchars)  // maybe multiple white space
                j++;  // find next arg, discarding white spaces, within numchars
            while(buffer[j] != ' ' && i < sizeof(buffer) - 1 && j < numchars) {
                url[j] = buffer[j];   // pass if not white space
                i++;                  // pass if within range
                j++;                  // pass if j is in the range of the socket line
            }
            url[i] = '\0';  // put url into a cstring from buffer
        
            if(strcmp(method, "GET") == 0) {
                query_string = url;
                while(*query_string != '?' && *query_string != '\0')
                    query_string++;  // move to the next char if it's not ? or escape char
                if(*query_string == '?') {
                    cgi = 1;
                    *query_string = '\0';  // seperate url and param
                    query_string++;
                }
            }
            
            sprintf(path, "htdocs%s", url);  //不懂
            if(path[path.lenth - 1] == '/')   // 访问主页, index.html
                strcat(path, "index.html");

            /*                                  stat啥意思没懂
            if (stat(path, &st) == -1) {
                while ((numchars > 0) && strcmp("\n", buf))  // read & discard headers
                    numchars = get_line(client, buf, sizeof(buf));
                not_found(client);
            }
            else
            {
              if ((st.st_mode & S_IFMT) == S_IFDIR)
              strcat(path, "/index.html");
                 if ((st.st_mode & S_IXUSR) ||
                    (st.st_mode & S_IXGRP) ||
                    (st.st_mode & S_IXOTH))
                        cgi = 1;
             if (!cgi)
                 serve_file(client, path);
            else
                execute_cgi(client, path, method, query_string);
            }
            */

            close(client);
        }

        int get_line(int socket, char *buffer, int size) {
            int i = 0, n = 0;
            char ch = '\0';
            while(i < size - 1 && ch != '\n') {
                n = recv(socket, &ch, 1, 0);
                /* DEBUG printf("%02X\n", c); 自带注释，不知道啥意思*/
                if(n > 0) {
                    if(ch == '\r') {
                        n = recv(socket, &ch, 1, MSG_PEEK);   // recv()干嘛的。。。
                        /* DEBUG printf("%02X\n", c); */
                        if(n > 0 && ch == '\n')
                            recv(socket, &ch, 1, 0);
                        else
                            ch = '\n'; 
                    }
                    buffer[i] = ch;
                    i++;
                }
                else
                    ch = '\n';
            }
            buffer[i] = '\0';
            return i;
        }



        
};

Tiny_http_accept_part::Tiny_http_accept_part(int i) {
    //client = (intptr_t)arg;
    cgi = 1;
}

int main() {
    Tiny_http_accept_part tmp(1);
    cout << tmp.test() << endl;
    return 0;
}