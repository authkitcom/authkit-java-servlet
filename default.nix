with import <nixpkgs> {};

stdenv.mkDerivation {

  name = "authkit-servlet";

  buildInputs = with pkgs; [
    git
    gitAndTools.gitflow
    jdk8
    gradle
  ];

  shellHook = ''
    if [ ! -d "./jdk" ]; then ln -s ${pkgs.jdk8}/lib/openjdk ./jdk; fi
    export JAVA_HOME="${pkgs.jdk8}/lib/openjdk"
  '';
}

