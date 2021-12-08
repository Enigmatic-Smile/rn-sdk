require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = package['name']
  s.version      = package['version']
  s.summary      = package['description']
  s.license      = package['license']
  s.authors      = package['author']
  s.homepage     = package['homepage']
  s.platform     = :ios, "9.1"

  s.source       = { :git => "https://github.com/FidelLimited/rn-sdk.git", :tag => "v#{s.version}" }
  s.source_files  = "ios/**/*.{h,m,swift}"

  s.xcconfig = { 'SWIFT_OBJC_BRIDGING_HEADER' => '../../node_modules/fidel-react-native/ios/Fidel-Bridging-Header.h' } 

  s.dependency 'React-Core'
  s.dependency 'Fidel'
end