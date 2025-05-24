#   Foteinos Konstantinos (2023086)

import os
import sys

if len(sys.argv)>1 and sys.argv[1].lower()=="--compile-only":
    compile_only = True
else:
    compile_only = False

delimeter = ';' if os.sep=='\\' else ':'

print("\nCompiling the java source files ...\n")
res = os.system(f"javac -cp \"..{os.sep}..{os.sep}lib{os.sep}servlet-api.jar{delimeter}.{os.sep}WEB-INF{os.sep}lib{os.sep}gson-2.13.1.jar\" \
            -d .{os.sep}WEB-INF{os.sep}classes .{os.sep}WEB-INF{os.sep}src{os.sep}com{os.sep}topicexchange{os.sep}*.java")

if res!=0:
    print(f"\nCompilation failed with exit code {res}\n")
    if not compile_only:
        ans = input("Are you sure you want to continue? Enter No or N instead:").lower()
        if ans=='no' or ans=='n':
            exit()
    else:
        exit()
else:
    print(f"\nCompilation was successfull.\n")

if compile_only:
    exit()

cwd = os.getcwd()
os.chdir(f"..{os.sep}..")
end = 'bat' if os.sep=='\\' else 'sh'

print("\nStarting Apache Tomcat ...\n")
res = os.system(f".{os.sep}bin{os.sep}startup.{end}")

if res!=0:
    print(f"\nExecution failed with exit code {res}\n")
else:
    print(f"\nExecution was successfull.\n")

os.chdir(cwd)